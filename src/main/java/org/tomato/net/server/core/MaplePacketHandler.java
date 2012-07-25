package org.tomato.net.server.core;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;

/**
 * The basic handler for networking actions.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface MaplePacketHandler {
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified org.tomato.client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified org.tomato.client
	 */
	public void process(ChannelBuffer slea, KeepAliveClient c);
	
	/**
	 * Validates the state of the specified org.tomato.client.
	 * @param client the org.tomato.client to be validated
	 * @return whether or not the org.tomato.client is valid
	 */
	public boolean validate(Client client);
}
