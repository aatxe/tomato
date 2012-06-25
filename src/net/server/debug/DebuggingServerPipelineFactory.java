package net.server.debug;

import java.util.ArrayList;
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
public class DebuggingServerPipelineFactory implements ChannelPipelineFactory {
		ArrayList<MapleServerHandler> binds = new ArrayList<MapleServerHandler>();
		
		@Override
		public ChannelPipeline getPipeline() {
			MapleServerHandler tmp = new MapleServerHandler();
			binds.add(tmp);
			ChannelPipeline cp = Channels.pipeline(tmp);
			cp.addLast("customDecoder", new MaplePacketDecoder());
			cp.addLast("customEncoder", new MaplePacketEncoder());
			return cp;
		}
		
		public void debugWriteAll(Object message) {
			for (MapleServerHandler msh : binds) {
				msh.getConnections().write(message);
			}
		}
}
