package net.server.core;

import client.MapleClient;

/**
 * A <code>MaplePacketHandler</code> with validation based on the state of the client's connection.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public abstract class AbstractMaplePacketHandler implements MaplePacketHandler {
	@Override
	public boolean validate(MapleClient c) {
		return c.isConnected();
	}

}
