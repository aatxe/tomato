package org.tomato.tools.data.input;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * A <code>SeekableInputStreamBytestream</code> wrapping a <code>RandomAccessFile</code>.
 * @author tomato
 * @version 1.0
 * @since 0.1
 */
public class RandomAccessByteStream implements SeekableInputStreamBytestream {
	private RandomAccessFile raf;
	private long bytesRead = 0;

	/**
	 * Creates a <code>SeekableInputStreamBytestream</code> wrapping a <code>RandomAccessFile</code>
	 * @param raf the <code>RandomAccessFile</code> to wrap
	 */
	public RandomAccessByteStream(RandomAccessFile raf) {
		super();
		this.raf = raf;
	}

	@Override
	public int readByte() {
		try {
			int tmp = raf.read();
			if (tmp == -1) {
				throw new RuntimeException("EOF");
			}
			bytesRead++;
			return tmp;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void seek(long offset) throws IOException {
		raf.seek(offset);
	}

	@Override
	public long getPosition() throws IOException {
		return raf.getFilePointer();
	}

	@Override
	public long getBytesRead() {
		return bytesRead;
	}

	@Override
	public long available() {
		try {
			return raf.length() - raf.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
