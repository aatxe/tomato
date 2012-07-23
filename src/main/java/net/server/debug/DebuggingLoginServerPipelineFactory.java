package net.server.debug;

import java.util.ArrayList;
import net.server.core.MapleServerHandler;
import net.server.encryption.MaplePacketDecoder;
import net.server.encryption.MaplePacketEncoder;
import net.server.login.LoginServerHandler;
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
