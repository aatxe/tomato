package org.tomato.net.server.core;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.client.MapleClient;
import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;

/**
 * A <code>MaplePacketHandler</code> with validation based on the state of the org.tomato.client's connection.
 * @author tomato
 * @version 1.1
 * @since alpha
 */
public abstract class AbstractMaplePacketHandler implements MaplePacketHandler {
	@Override
	public void process(ChannelBuffer buffer, KeepAliveClient c) {
		this.process(buffer, (MapleClient) c);
	}
	
	@Override
	public boolean validate(Client c) {
		return c.isConnected();
	}
	
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified org.tomato.client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified org.tomato.client
	 */
	public abstract void process(ChannelBuffer buffer, MapleClient c);
}
