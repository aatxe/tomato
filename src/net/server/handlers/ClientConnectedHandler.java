package net.server.handlers;

import net.server.core.AbstractMaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;

/**
 * A handler for packets indicating a client has connected.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientConnectedHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, MapleClient c) {
		c.scheduleKeepAlive();
		// TODO: sort out TomatoJuice clients.
	}
}
