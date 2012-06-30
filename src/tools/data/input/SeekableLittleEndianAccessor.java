package tools.data.input;

/**
 * A seekable <code>LittleEndianAccessor</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public interface SeekableLittleEndianAccessor extends LittleEndianAccessor {
	/**
	 * Skips forward in the accessor.
	 * @param offset the amount to skip forward by
	 */
	void seek(long offset);
	
	/**
	 * Gets the current position of the seeking cursor.
	 * @return the current position of the cursor
	 */
	long getPosition();
}
