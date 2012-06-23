package tools.data.input;

import java.io.IOException;
import java.io.InputStream;
import tools.ConsoleOutput;

/**
 * A <code>ByteInputStream</code> wrapping an <code>InputStream</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class InputStreamByteStream implements ByteInputStream {
	private InputStream is;
	private long bytesRead = 0;

	/**
	 * Creates a byte stream wrapping an <code>InputStream</code>.
	 * @param is the <code>InputStream</code> to wrap the byte stream around
	 */
	public InputStreamByteStream(InputStream is) {
		this.is = is;
	}

	@Override
	public int readByte() {
		try {
			int tmp = is.read();
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
	public long getBytesRead() {
		return bytesRead;
	}

	@Override
	public long available() {
		try {
			return is.available();
		} catch (IOException e) {
			ConsoleOutput.print("Error: " + e);
			return 0;
		}
	}
}
