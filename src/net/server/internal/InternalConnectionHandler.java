package net.server.internal;

import net.server.core.AbstractMapleServerHandler;
import net.server.core.MaplePacket;
import net.server.core.MaplePacketProcessor;
import net.server.encryption.MaplePacketDecoder;
import net.server.handlers.internal.HandshakeHandler;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import tools.ConsoleOutput;
import tools.HexTool;
import tools.data.input.ByteArrayByteStream;
import tools.data.input.GenericSeekableLittleEndianAccessor;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.net.InternalPacketCreator;
import client.internal.InternalClient;
import constants.SourceConstants;

/**
 * A handler for internal connections.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalConnectionHandler extends AbstractMapleServerHandler {
	private ChannelGroup connections = new DefaultChannelGroup("internal-connections");
	private InternalPacketProcessor processor;
	
	/**
	 * Creates a internal connection handler.
	 */
	public InternalConnectionHandler() {
		this.setPacketProcessor(InternalPacketProcessor.getInstance());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		ConsoleOutput.print("[Internal] Channel opened with " + e.getChannel().getRemoteAddress() + ".");
		connections.add(e.getChannel());
		InternalClient c = new InternalClient(e.getChannel());
		e.getChannel().setAttachment(c);
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		MaplePacketDecoder mpd = new MaplePacketDecoder();
		try {
			MaplePacket mp = (MaplePacket) mpd.decode(ctx, e.getChannel(), (ChannelBuffer) e.getMessage());
			if (mp != null) {
				byte[] data = mp.getBytes();
				if (SourceConstants.VERBOSE_PACKETS) ConsoleOutput.print("[I] [Recv] " + HexTool.toString(data));
				SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new ByteArrayByteStream(data));
				InternalClient client = (InternalClient) e.getChannel().getAttachment();
				InternalPacketHandler iph = processor.getHandler(slea.readShort());
				if (iph != null && iph.validate(client)) {
					try {
						iph.process(slea, client);
					} catch (Throwable t) {
						// TODO: log all packet processing exceptions.
					}
				}
			}
		} catch (NullPointerException ex) {
			SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new ByteArrayByteStream(((ChannelBuffer) e.getMessage()).array()));
			InternalClient client = (InternalClient) e.getChannel().getAttachment();
			short length = slea.readShort();
			if (slea.available() == length) {
				new HandshakeHandler().process(slea, client);
				e.getChannel().write(InternalPacketCreator.getConnected());
			}
		}
		
	}
	
	@Override
	public void setPacketProcessor(MaplePacketProcessor processor) {
		this.processor = (InternalPacketProcessor) processor;
	}
	
	@Override
	public ChannelGroup getConnections() {
		return connections;
	}
}
