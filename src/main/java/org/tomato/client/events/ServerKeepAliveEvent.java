package org.tomato.client.events;

import org.jboss.netty.channel.Channel;
import org.tomato.tools.net.WorldPacketCreator;
import org.tomato.client.core.KeepAliveClient;
import org.tomato.constants.SourceConstants;

public class ServerKeepAliveEvent extends AbstractEvent {
	/**
	 * Creates a runnable event wrapping a <code>Channel</code>
	 * @param session the <code>Channel</code> session to be wrapped
	 */
	public ServerKeepAliveEvent(Channel session) {
		super(session);
	}

	@Override
	public void run() {
		KeepAliveClient client = (KeepAliveClient) session.getAttachment();
		if (client.getTimeSinceLastKeepAlive() > SourceConstants.KEEPALIVE_TIMEOUT && client.getLastKeepAliveRecieved() > 0) {
			session.close();
		} else {
			session.write(WorldPacketCreator.getKeepAlive());
		}
	}
}
