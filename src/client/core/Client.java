package client.core;

import org.jboss.netty.channel.Channel;

public interface Client {
	/**
	 * Gets the channel wrapped by the client.
	 * @return the channel wrapped by the client
	 */
	public Channel getChannel();
	
	/**
	 * Gets whether or not the client is connected.
	 * @return whether or not the client is connected
	 */
	public boolean isConnected();
	
	/**
	 * Closes the client's session, disconnecting them.
	 */
	public void disconnect();
}
