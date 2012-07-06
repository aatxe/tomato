package client.core;

/**
 * An interface describing a client using Keep Alive.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public interface KeepAliveClient extends Client {
	/**
	 * Tells the client that it has recieved a KeepAlive packet.
	 */
	public void keepAliveRecieved();
	
	/**
	 * Gets the timestamp of the last KeepAlive packet received in milliseconds.
	 * @return the timestamp of the last KeepAlive packet recieved 
	 */
	public long getLastKeepAliveRecieved();
	
	/**
	 * Gets the time since the last KeepAlive packet was recieved in milliseconds.
	 * @return the time since the last KeepAlive packet was recieved
	 */
	public long getTimeSinceLastKeepAlive();
	
	/**
	 * Schedules a <code>Runnable</code> to send KeepAlive packets to the client.
	 */
	public void scheduleKeepAlive();
}
