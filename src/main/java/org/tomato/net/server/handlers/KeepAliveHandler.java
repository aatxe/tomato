package org.tomato.net.server.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.client.MapleClient;

/**
 * A handler for packets ensuring the connection is still alive. 
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class KeepAliveHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(ChannelBuffer buffer, MapleClient c) {
		c.keepAliveRecieved();
	}
}
