package client.events;

import org.jboss.netty.channel.Channel;
import tools.net.LoginPacketCreator;
import constants.SourceConstants;
import client.MapleClient;

/**
 * A KeepAlive event, to send clients KeepAlive packets, and close dead connections.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class KeepAliveEvent extends AbstractEvent {
	/**
	 * Creates a runnable event wrapping a <code>Channel</code>
	 * @param session the <code>Channel</code> session to be wrapped
	 */
	public KeepAliveEvent(Channel session) {
		super(session);
	}

	@Override
	public void run() {
		MapleClient client = (MapleClient) session.getAttachment();
		if (client.getTimeSinceLastKeepAlive() > SourceConstants.KEEPALIVE_TIMEOUT && client.getLastKeepAliveRecieved() > 0) {
			session.close();
		} else {
			session.write(LoginPacketCreator.getKeepAlive());
		}
	}

}
