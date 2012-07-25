package org.tomato.net.server.world;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.core.MaplePacketHandler;
import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;
import org.tomato.client.world.ServerClient;

/**
 * A <code>MaplePacketHandler</code> with validation based on the state of the org.tomato.client's connection.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public abstract class AbstractWorldPacketHandler implements MaplePacketHandler {
	@Override
	public void process(ChannelBuffer buffer, KeepAliveClient c) {
		this.process(buffer, (ServerClient) c);
	}
	
	@Override
	public boolean validate(Client c) {
		return c.isConnected();
	}
	
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified org.tomato.client.
	 * @param buffer the seekable accessor wrapping the packet
	 * @param c the specified org.tomato.client
	 */
	public abstract void process(ChannelBuffer buffer, ServerClient c);
}
