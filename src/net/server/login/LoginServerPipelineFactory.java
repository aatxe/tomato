package net.server.login;

import net.server.encryption.MaplePacketDecoder;
import net.server.encryption.MaplePacketEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * A <code>ChannelPipelineFactory</code> for login server pipelines.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServerPipelineFactory implements ChannelPipelineFactory {
	private LoginServerHandler lsh;
	
	@Override
	public ChannelPipeline getPipeline() {
		lsh = new LoginServerHandler();
		ChannelPipeline cp = Channels.pipeline(lsh);
		cp.addLast("customDecoder", new MaplePacketDecoder());
		cp.addLast("customEncoder", new MaplePacketEncoder());
		return cp;
	}
}
