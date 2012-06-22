package net.server.core;

import net.encryption.MapleObfuscator;
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
import client.MapleClient;
import constants.ServerConstants;

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
		e.getChannel().write(MaplePacketCreator.getHello(ServerConstants.MAJOR_VERSION, ServerConstants.MINOR_VERSION, ivSend, ivRecv));
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
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// TODO: implement message retrieval.
		byte[] data = ((ChannelBuffer) e.getMessage()).array();
		System.out.println(HexTool.toString(data));
	}
	
	/*@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
		/*Runnable r = ((MaplePacket) e.getMessage()).getOnSend();
		if (r != null) {
			r.run();
		}/
		super.writeRequested(ctx, e);
	}*/
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getChannel().close();
		e.getCause().printStackTrace();
		// TODO: log networking exceptions.
	}
}
