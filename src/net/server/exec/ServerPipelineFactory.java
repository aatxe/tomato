package net.server.exec;

import net.server.core.MapleServerHandler;
import net.server.encryption.MaplePacketDecoder;
import net.server.encryption.MaplePacketEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * A <code>ChannelPipelineFactory</code> for server instances.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ServerPipelineFactory implements ChannelPipelineFactory {
	@Override
	public ChannelPipeline getPipeline() {
		ChannelPipeline cp = Channels.pipeline(new MapleServerHandler()); // TODO: pipeline(new MapleServerHandler)
		cp.addLast("customDecoder", new MaplePacketDecoder());
		cp.addLast("customEncoder", new MaplePacketEncoder());
		return cp;
	}
}
