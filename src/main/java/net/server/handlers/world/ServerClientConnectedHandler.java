package net.server.handlers.world;

import net.server.world.AbstractWorldPacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import client.world.ServerClient;

/**
 * A handler packets indicating that a <code>ServerClient</code> has connected.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class ServerClientConnectedHandler extends AbstractWorldPacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, ServerClient c) {
		c.scheduleKeepAlive();
	}
}
