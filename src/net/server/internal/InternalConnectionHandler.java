package net.server.internal;

import net.server.core.AbstractMapleServerHandler;
import net.server.core.MaplePacket;
import net.server.core.MaplePacketProcessor;
import net.server.encryption.MaplePacketDecoder;
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
		ConsoleOutput.print("Channel opened with " + e.getChannel().getRemoteAddress() + ".");
		connections.add(e.getChannel());
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		MaplePacketDecoder mpd = new MaplePacketDecoder();
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
