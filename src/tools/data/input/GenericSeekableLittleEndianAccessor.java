package tools.data.input;

import java.io.IOException;
import tools.ConsoleOutput;

public class GenericSeekableLittleEndianAccessor extends GenericLittleEndianAccessor implements SeekableLittleEndianAccessor {
	private SeekableInputStreamBytestream bs;

	public GenericSeekableLittleEndianAccessor(SeekableInputStreamBytestream bs) {
		super(bs);
		this.bs = bs;
	}

	public void seek(long offset) {
		try {
			bs.seek(offset);
		} catch (IOException e) {
			ConsoleOutput.print("Failed to seek: " + e);
		}
	}
	
	@Override
	public long getPosition() {
		try {
			return bs.getPosition();
		} catch (IOException e) {
			ConsoleOutput.print("Failed to get position: " + e);
			return -1;
		}
	}
	
	@Override
	public void skip(int amount) {
		seek(getPosition() + amount);
	}
}
