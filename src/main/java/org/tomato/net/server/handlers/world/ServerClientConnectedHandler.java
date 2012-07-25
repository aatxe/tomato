package org.tomato.net.server.handlers.world;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.world.AbstractWorldPacketHandler;
import org.tomato.client.world.ServerClient;

/**
 * A handler packets indicating that a <code>ServerClient</code> has connected.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class ServerClientConnectedHandler extends AbstractWorldPacketHandler {
	@Override
	public void process(ChannelBuffer buffer, ServerClient c) {
		c.scheduleKeepAlive();
	}
}
