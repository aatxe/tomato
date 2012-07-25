package org.tomato.net.server.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.client.MapleClient;

/**
 * A handler for packets indicating a org.tomato.client has connected.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientConnectedHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(ChannelBuffer buffer, MapleClient c) {
		c.scheduleKeepAlive();
		// TODO: sort out TomatoJuice clients.
	}
}
