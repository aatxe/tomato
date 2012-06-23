package tools.data.input;

import java.awt.Point;

/**
 * An accessor for little endian byte sequences.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface LittleEndianAccessor {
	/**
	 * Reads the next byte from the byte sequence.
	 * @return the next byte in the byte sequence
	 */
	public byte readByte();
	
	/**
	 * Reads the next char from the byte sequence.
	 * @return the next char in the byte sequence
	 */
	public char readChar();
	
	/**
	 * Reads the next short from the byte sequence.
	 * @return the next short in the byte sequence
	 */
	public short readShort();
	
	/**
	 * Reads the next int from the byte sequence.
	 * @return the next int in the byte sequence
	 */
	public int readInt();
	
	/**
	 * Reads the next <code>Point</code> from the byte sequence.
	 * @return the next <code>Point</code> in the byte sequence
	 */
	public Point readPos();
	
	/**
	 * Reads the next long from the byte sequence.
	 * @return the next long in the byte sequence
	 */
	public long readLong();
	
	/**
	 * Skips ahead in the byte sequence by the specified amount.
	 * @param num the amount to skip ahead by
	 */
	public void skip(int num);
	
	/**
	 * Reads a sequence of bytes from the byte sequence.
	 * @param num the amount of bytes to read
	 * @return the bytes read as an array of bytes
	 */
	public byte[] read(int num);
	
	/**
	 * Reads the next float from the byte sequence.
	 * @return the next flaot in the byte sequence
	 */
	public float readFloat();
	
	/**
	 * Reads the next double from the byte sequence.
	 * @return the next double in the byte sequence
	 */
	public double readDouble();
	
	/**
	 * Reads the next ASCII string of the specified length from the byte sequence.
	 * @param n the length of the ASCII string
	 * @return the next ASCII string in the byte sequence
	 */
	public String readAsciiString(int n);

	/**
	 * Reads the next null-terminated ASCII string from the byte sequence.
	 * @return the next null-terminated ASCII string in the byte sequence
	 */
	public String readNullTerminatedAsciiString();
	
	/**
	 * Reads the next Maple-formatted ASCII string from the byte sequence.
	 * @return the next Maple-formatted ASCII string in the byte sequence
	 */
	public String readMapleAsciiString();
	
	/**
	 * Gets the number of bytes read.
	 * @return the number of bytes read
	 */
	public long getBytesRead();
	
	/**
	 * Gets the number of bytes available for reading.
	 * @return the number of bytes available for reading
	 */
	public long available();
}
