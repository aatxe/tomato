package org.tomato.client.core;

import org.jboss.netty.channel.Channel;

public interface Client {
	/**
	 * Gets the channel wrapped by the org.tomato.client.
	 * @return the channel wrapped by the org.tomato.client
	 */
	public Channel getChannel();
	
	/**
	 * Gets whether or not the org.tomato.client is connected.
	 * @return whether or not the org.tomato.client is connected
	 */
	public boolean isConnected();
	
	/**
	 * Closes the org.tomato.client's session, disconnecting them.
	 */
	public void disconnect();
}
