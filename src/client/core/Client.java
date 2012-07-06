package client.core;

public interface Client {
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
