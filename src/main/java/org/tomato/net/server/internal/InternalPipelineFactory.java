package org.tomato.net.server.internal;

import org.tomato.net.server.encryption.MaplePacketDecoder;
import org.tomato.net.server.encryption.MaplePacketEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * A pipeline factory for internal connection handlers.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalPipelineFactory implements ChannelPipelineFactory {
	@Override
	public ChannelPipeline getPipeline() {
		ChannelPipeline cp = Channels.pipeline(new InternalConnectionHandler());
		cp.addLast("customDecoder", new MaplePacketDecoder());
		cp.addLast("customEncoder", new MaplePacketEncoder());
		return cp;
	}
}
