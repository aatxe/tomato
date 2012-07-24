package org.tomato.tools.data.input;

/**
 * An input stream for bytes.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface ByteInputStream {
	/**
	 * Reads a byte from the input stream.
	 * @return a byte from the input stream
	 */
	public int readByte();

	/**
	 * Gets the amount of bytes read.
	 * @return the amount of bytes read
	 */
	public long getBytesRead();

	/**
	 * Gets the amount of bytes available for reading.
	 * @return the amount of bytes available for reading
	 */
	public long available();
}
