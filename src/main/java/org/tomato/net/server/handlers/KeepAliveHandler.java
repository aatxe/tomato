package org.tomato.net.server.handlers;

import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.tools.data.input.SeekableLittleEndianAccessor;
import org.tomato.client.MapleClient;

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
