package tools.data.output;

/**
 * A basic output stream for bytes.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
interface ByteOutputStream {
	/**
	 * Writes a byte to the stream.
	 * @param b the byte to write.
	 */
	public void writeByte(byte b);
}
