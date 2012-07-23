package net.server.world;

import net.server.core.MaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import client.core.Client;
import client.core.KeepAliveClient;
import client.world.ServerClient;

/**
 * A <code>MaplePacketHandler</code> with validation based on the state of the client's connection.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public abstract class AbstractWorldPacketHandler implements MaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, KeepAliveClient c) {
		this.process(slea, (ServerClient) c);
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
	public abstract void process(SeekableLittleEndianAccessor slea, ServerClient c);
}
