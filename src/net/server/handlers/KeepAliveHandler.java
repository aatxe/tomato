package net.server.handlers;

import net.server.core.AbstractMaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;

/**
 * A handler for packets ensuring the connection is still alive. 
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class KeepAliveHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, MapleClient c) {
		c.keepAliveRecieved();
	}
}
