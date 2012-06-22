package tools.data.input;

import java.io.IOException;

public interface SeekableInputStreamBytestream extends ByteInputStream {
	public void seek(long offset) throws IOException;
	public long getPosition() throws IOException;
}
