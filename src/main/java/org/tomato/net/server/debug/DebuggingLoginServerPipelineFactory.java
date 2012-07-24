package org.tomato.net.server.debug;

import java.util.ArrayList;
import org.tomato.net.server.core.MapleServerHandler;
import org.tomato.net.server.encryption.MaplePacketDecoder;
import org.tomato.net.server.encryption.MaplePacketEncoder;
import org.tomato.net.server.login.LoginServerHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
/**
 * A <code>ChannelPipelineFactory</code> for server instances.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class DebuggingLoginServerPipelineFactory implements ChannelPipelineFactory {
		ArrayList<LoginServerHandler> binds = new ArrayList<LoginServerHandler>();
		
		@Override
		public ChannelPipeline getPipeline() {
			LoginServerHandler tmp = new LoginServerHandler();
			binds.add(tmp);
			ChannelPipeline cp = Channels.pipeline(tmp);
			cp.addLast("customDecoder", new MaplePacketDecoder());
			cp.addLast("customEncoder", new MaplePacketEncoder());
			return cp;
		}
		
		/**
		 * Writes a packet for debugging to all bound login servers.
		 * @param message the message to write
		 */
		public void debugWriteAll(Object message) {
			for (MapleServerHandler msh : binds) {
				msh.getConnections().write(message);
			}
		}
}
