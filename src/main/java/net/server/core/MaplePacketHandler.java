package net.server.core;

import tools.data.input.SeekableLittleEndianAccessor;
import client.core.Client;
import client.core.KeepAliveClient;

/**
 * The basic handler for networking actions.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface MaplePacketHandler {
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified client
	 */
	public void process(SeekableLittleEndianAccessor slea, KeepAliveClient c);
	
	/**
	 * Validates the state of the specified client.
	 * @param client the client to be validated
	 * @return whether or not the client is valid
	 */
	public boolean validate(Client client);
}
