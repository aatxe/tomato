package org.tomato.net.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.net.server.encryption.MaplePacketDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.tomato.tools.HexTool;
import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;
import org.tomato.constants.SourceConstants;

public abstract class AbstractMapleServerHandler extends SimpleChannelHandler implements MapleServerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMapleServerHandler.class);
    
	private ChannelGroup connections = new DefaultChannelGroup();
	private AbstractMaplePacketProcessor processor;
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		synchronized (e.getChannel()) {
			Client client = (Client) e.getChannel().getAttachment();
			if (client != null) {
				try {
					client.disconnect();
				} finally {
					e.getChannel().setAttachment(null);
					// TODO: empty org.tomato.client.
				}
			}
		}
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		MaplePacketDecoder mpd = new MaplePacketDecoder();
		MaplePacket mp = (MaplePacket) mpd.decode(ctx, e.getChannel(), (ChannelBuffer) e.getMessage());
		if (mp != null) {
			byte[] data = mp.getBytes();
			if (SourceConstants.VERBOSE_PACKETS) LOGGER.debug("Received {}", HexTool.toString(data));
			ChannelBuffer buffer = ChannelBuffers.wrappedBuffer(data);
			KeepAliveClient client = (KeepAliveClient) e.getChannel().getAttachment();
			MaplePacketHandler mph = processor.getHandler(buffer.readShort());
			if (mph != null && mph.validate(client)) {
				try {
					mph.process(buffer, client);
				} catch (Throwable t) {
					t.printStackTrace();
					// TODO: log all packet processing exceptions.
				}
			}
		} else if (SourceConstants.VERBOSE_PACKETS) {
			LOGGER.debug("Packet dropped");
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getChannel().close();
		e.getCause().printStackTrace();
		// TODO: log networking exceptions.
	}

	@Override
	public void setPacketProcessor(MaplePacketProcessor processor) {
		this.processor = (AbstractMaplePacketProcessor) processor;
	}
	
	@Override
	public ChannelGroup getConnections() {
		return connections;
	}
}
