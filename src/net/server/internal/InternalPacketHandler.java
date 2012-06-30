package net.server.internal;

import tools.data.input.SeekableLittleEndianAccessor;
import client.internal.InternalClient;

/**
 * An interface for packet handlers for internal connections.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public abstract class InternalPacketHandler {
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified client
	 */
	public abstract void process(SeekableLittleEndianAccessor slea, InternalClient c);
	
	/**
	 * Validates the state of the specified client.
	 * @param c the client to be validated
	 * @return whether or not the client is valid
	 */
	public boolean validate(InternalClient c) {
		return c.isConnected();
	}
}
