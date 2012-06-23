package net.server.core;

/**
 * The basis of all networking actions, a MaplePacket.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface MaplePacket {
	/**
	 * Gets the content of the packet.
	 * @return the content of the packet as a byte array.
	 */
	public byte[] getBytes();
	/**
	 * Gets the <code>Runnable</code> to be executed on send.
	 * @return the <code>Runnable</code> to be executed on send
	 */
	public Runnable getOnSend();
	/**
	 * Sets the <code>Runnable</code> to be executed on send.
	 * @param onSend the <code>Runnable</code> to be executed on send.
	 */
	public void setOnSend(Runnable onSend);
}
