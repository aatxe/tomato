package org.tomato.tools.data.input;

import java.io.IOException;

/**
 * An interface for a seekable <code>ByteInputStream</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface SeekableInputStreamBytestream extends ByteInputStream {
	/**
	 * Seeks to the desired offset in the stream.
	 * @param offset the amount by which to seek
	 * @throws IOException
	 */
	public void seek(long offset) throws IOException;
	
	/**
	 * Gets the current position of the seeking cursor.
	 * @return the current position of the seeking cursor
	 * @throws IOException
	 */
	public long getPosition() throws IOException;
}
