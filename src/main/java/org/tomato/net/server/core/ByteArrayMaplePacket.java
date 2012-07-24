package org.tomato.net.server.core;

/**
 * A MaplePacket based around a byte array.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ByteArrayMaplePacket implements MaplePacket {
	Runnable onSend = null;
	private byte[] data;
	
	/**
	 * Creates a MaplePacket wrapping a byte array.
	 * @param data the byte array to be wrapped
	 */
	public ByteArrayMaplePacket(byte[] data) {
		this(data, null);
	}
	
	/**
	 * Creates a MaplePacket wrapping a byte array, with a <code>Runnable</code> to be executed on send.
	 * @param data the byte array to be wrapped
	 * @param onSend the <code>Runnable</code> to be executed on send
	 */
	public ByteArrayMaplePacket(byte[] data, Runnable onSend) {
		this.data = data;
		this.onSend = onSend;
	}
	
	@Override
	public byte[] getBytes() {
		return data;
	}

	@Override
	public Runnable getOnSend() {
		return onSend;
	}

	@Override
	public void setOnSend(Runnable onSend) {
		this.onSend = onSend;
	}
}
