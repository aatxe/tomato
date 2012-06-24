package net.server.core;

import client.MapleClient;

public abstract class AbstractMaplePacketHandler implements MaplePacketHandler {
	@Override
	public boolean validate(MapleClient c) {
		return c.isConnected();
	}

}
