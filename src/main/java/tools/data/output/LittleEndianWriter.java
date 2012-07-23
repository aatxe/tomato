package tools.data.output;

import java.awt.Point;

/**
 * A writer for a little endian sequence of bytes.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface LittleEndianWriter {
	/**
	 * Write an array of bytes to the sequence.
	 * @param b the bytes to write.
	 */
	public void write(byte b[]);

	/**
	 * Write a byte to the sequence.
	 * @param b the byte to write.
	 */
	public void write(byte b);

	/**
	 * Write a byte in integer form to the sequence.
	 * @param b the byte as an <code>Integer</code> to write.
	 */
	public void write(int b);

	public void skip(int b);

	/**
	 * Writes an integer to the sequence.
	 * @param i the integer to write.
	 */
	public void writeInt(int i);

	/**
	 * Write a short integer to the sequence.
	 * @param s the short integer to write.
	 */
	public void writeShort(int s);

	/**
	 * Write a long integer to the sequence.
	 * @param l the long integer to write.
	 */
	public void writeLong(long l);

	/**
	 * Writes an ASCII string the the sequence.
	 * @param s the ASCII string to write.
	 */
	void writeAsciiString(String s);

	/**
	 * Writes a null-terminated ASCII string to the sequence.
	 * @param s the ASCII string to write.
	 */
	void writeNullTerminatedAsciiString(String s);

	/**
	 * Writes a Maple-formatted ASCII string to the sequence.
	 * @param s the ASCII string to use Maple-formatted to write.
	 */
	void writeMapleAsciiString(String s);

	/**
	 * Writes a 2D 4 byte position information to the sequence.
	 * @param s the Point position to write.
	 */
	void writePos(Point s);
}
