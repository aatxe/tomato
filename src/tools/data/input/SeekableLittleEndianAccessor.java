package tools.data.input;

/**
 * A seekable <code>LittleEndianAccessor</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface SeekableLittleEndianAccessor extends LittleEndianAccessor {
	void seek(long offset);
	long getPosition();
}
