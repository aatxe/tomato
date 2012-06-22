package tools.data.input;

import java.io.IOException;
import java.io.InputStream;
import tools.ConsoleOutput;

public class InputStreamByteStream implements ByteInputStream {
	private InputStream is;
	private long bytesRead = 0;

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
