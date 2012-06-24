package net.server.core;

import net.encryption.MapleObfuscator;
import net.server.encryption.MaplePacketDecoder;
import net.tools.MaplePacketCreator;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import tools.ConsoleOutput;
import tools.HexTool;
import tools.data.input.ByteArrayByteStream;
import tools.data.input.GenericSeekableLittleEndianAccessor;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;
import constants.ServerConstants;

/**
 * A Netty handler for Server NIO.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MapleServerHandler extends SimpleChannelHandler {
	ChannelGroup connections = new DefaultChannelGroup("client-connections");
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		ConsoleOutput.print("Channel opened with " + e.getChannel().getRemoteAddress() + ".");
		connections.add(e.getChannel());
		byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
		byte ivRecv[] = {70, 114, 122, 82};
		byte ivSend[] = {82, 48, 120, 115};
		ivRecv[3] = (byte) (Math.random() * 255);
		ivSend[3] = (byte) (Math.random() * 255);
		MapleObfuscator send = new MapleObfuscator(key, ivSend, (short) (0xFFFF - ServerConstants.MAJOR_VERSION));
		MapleObfuscator recv = new MapleObfuscator(key, ivRecv, (short) ServerConstants.MAJOR_VERSION);
		MapleClient client = new MapleClient(e.getChannel(), send, recv);
		// TODO: set client's world and channel.
		e.getChannel().write(MaplePacketCreator.getHandshake(ServerConstants.MAJOR_VERSION, ServerConstants.MINOR_VERSION, ivSend, ivRecv));
		e.getChannel().setAttachment(client);
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		synchronized (e.getChannel()) {
			MapleClient client = (MapleClient) e.getChannel().getAttachment();
			if (client != null) {
				try {
					client.disconnect();
				} finally {
					e.getChannel().setAttachment(null);
					// TODO: empty client.
				}
			}
		}
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		MaplePacketDecoder mpd = new MaplePacketDecoder();
		MaplePacket mp = (MaplePacket) mpd.decode(ctx, e.getChannel(), (ChannelBuffer) e.getMessage());
		byte[] data = mp.getBytes();
		System.out.println(HexTool.toString(data));
		SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new ByteArrayByteStream(data));
		MapleClient client = (MapleClient) e.getChannel().getAttachment();
		MaplePacketHandler mph = MaplePacketProcessor.getInstance().getHandler(slea.readShort());
		if (mph != null && mph.validate(client)) {
			try {
				mph.process(slea, client);
			} catch (Throwable t) {
				// TODO: log all packet processing exceptions.
			}
		}
		e.getChannel().write(new ByteArrayMaplePacket(new byte[]{0, 0}));
	}
	
	/*@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Runnable r = ((MaplePacket) e.getMessage()).getOnSend();
		if (r != null) {
			r.run();
		}
	}*/
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getChannel().close();
		e.getCause().printStackTrace();
		// TODO: log networking exceptions.
	}
}
