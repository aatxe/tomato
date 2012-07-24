package org.tomato.net.server.core;

import org.jboss.netty.channel.group.ChannelGroup;

/**
 * An interface for some very generic server handlers.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public interface MapleServerHandler {
	/**
	 * Sets the packet processor to be used by the server.
	 * @param processor the <code>MaplePacketProcessor</code> to be used by the server
	 */
	public void setPacketProcessor(MaplePacketProcessor processor);
	
	/**
	 * Gets all of the connections to the server as a <code>ChannelGroup</code>.
	 * @return a <code>ChannelGroup</code> of all the connections.
	 */
	public ChannelGroup getConnections();
}
