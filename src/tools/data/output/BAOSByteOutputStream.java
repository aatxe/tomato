package tools.data.output;

import java.io.ByteArrayOutputStream;

/**
 * A <code>ByteOutputStream</code> wrapped by a <code>ByteArrayOutputStream</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
class BAOSByteOutputStream implements ByteOutputStream {
	private ByteArrayOutputStream baos;

	/**
	 * Creates a <code>ByteOutputStream</code> wrapped by a <code>ByteArrayOutputStream</code>.
	 * @param baos
	 */
	public BAOSByteOutputStream(ByteArrayOutputStream baos) {
		super();
		this.baos = baos;
	}

	@Override
	public void writeByte(byte b) {
		baos.write(b);
	}
}
