package org.tomato.net.server.internal;

import org.tomato.net.server.core.AbstractMapleServerHandler;
import org.tomato.net.server.core.MaplePacket;
import org.tomato.net.server.core.MaplePacketProcessor;
import org.tomato.net.server.encryption.MaplePacketDecoder;
import org.tomato.net.server.handlers.internal.HandshakeHandler;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.tomato.tools.ConsoleOutput;
import org.tomato.tools.HexTool;
import org.tomato.tools.data.input.ByteArrayByteStream;
import org.tomato.tools.data.input.GenericSeekableLittleEndianAccessor;
import org.tomato.tools.data.input.SeekableLittleEndianAccessor;
import org.tomato.client.internal.InternalClient;
import org.tomato.constants.SourceConstants;

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
				if (SourceConstants.VERBOSE_PACKETS) ConsoleOutput.print("[Recv] " + HexTool.toString(data));
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
