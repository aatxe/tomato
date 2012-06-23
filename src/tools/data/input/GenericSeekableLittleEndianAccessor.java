package tools.data.input;

import java.io.IOException;
import tools.ConsoleOutput;

/**
 * A <code>SeekableLittleEndianAccessor</code> wrapping a <code>ByteArrayByteStream</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class GenericSeekableLittleEndianAccessor extends GenericLittleEndianAccessor implements SeekableLittleEndianAccessor {
	private SeekableInputStreamBytestream bs;
	
	/**
	 * Creates a new generic <code>SeekableLittleEndianAccessor</code>.
	 * @param byteArrayByteStream the <code>ByteArrayByteStream</code> for the accessor to wrap
	 */
	public GenericSeekableLittleEndianAccessor(ByteArrayByteStream byteArrayByteStream) {
		super(byteArrayByteStream);
		this.bs = byteArrayByteStream;
	}

	@Override
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
