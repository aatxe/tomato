package net.server.core;

import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;
import client.core.Client;
import client.core.KeepAliveClient;

/**
 * A <code>MaplePacketHandler</code> with validation based on the state of the client's connection.
 * @author tomato
 * @version 1.1
 * @since alpha
 */
public abstract class AbstractMaplePacketHandler implements MaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, KeepAliveClient c) {
		this.process(slea, (MapleClient) c);
	}
	
	@Override
	public boolean validate(Client c) {
		return c.isConnected();
	}
	
	/**
	 * Processes a packet wrapped by a seekable accessor for a specified client.
	 * @param slea the seekable accessor wrapping the packet
	 * @param c the specified client
	 */
	public abstract void process(SeekableLittleEndianAccessor slea, MapleClient c);
}
