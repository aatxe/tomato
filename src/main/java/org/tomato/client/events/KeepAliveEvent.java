package org.tomato.client.events;

import org.jboss.netty.channel.Channel;
import org.tomato.tools.net.GlobalPacketCreator;
import org.tomato.client.core.KeepAliveClient;
import org.tomato.constants.SourceConstants;

/**
 * A KeepAlive event, to send clients KeepAlive packets, and close dead connections.
 * @author tomato
 * @version 1.1
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
		KeepAliveClient client = (KeepAliveClient) session.getAttachment();
		if (client.getTimeSinceLastKeepAlive() > SourceConstants.KEEPALIVE_TIMEOUT && client.getLastKeepAliveRecieved() > 0) {
			session.close();
		} else {
			session.write(GlobalPacketCreator.getKeepAlive());
		}
	}
}
