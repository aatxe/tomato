package org.tomato.net.server.internal;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.client.internal.InternalClient;

/**
 * An interface for packet handlers for internal connections.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public abstract class InternalPacketHandler {
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified org.tomato.client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified org.tomato.client
	 */
	public abstract void process(ChannelBuffer buffer, InternalClient c);
	
	/**
	 * Validates the state of the specified org.tomato.client.
	 * @param c the org.tomato.client to be validated
	 * @return whether or not the org.tomato.client is valid
	 */
	public boolean validate(InternalClient c) {
		return c.isConnected();
	}
}
