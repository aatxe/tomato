package client.events;

import net.tools.MaplePacketCreator;
import org.jboss.netty.channel.Channel;
import constants.SourceConstants;
import client.MapleClient;

public class KeepAlive implements Runnable {
	private Channel session;
	
	public KeepAlive(Channel session) {
		this.session = session;
	}
	
	@Override
	public void run() {
		MapleClient client = (MapleClient) session.getAttachment();
		if (client.getTimeSinceLastKeepAlive() > SourceConstants.KEEPALIVE_TIMEOUT && client.getLastKeepAliveRecieved() > 0) {
			session.close();
		} else {
			session.write(MaplePacketCreator.getKeepAlive());
		}
	}

}
