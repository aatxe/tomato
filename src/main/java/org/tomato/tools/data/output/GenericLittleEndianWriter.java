package org.tomato.tools.data.output;

import java.awt.Point;
import java.nio.charset.Charset;

/**
 * A generic <code>LittleEndianWriter</code> wrapping a <code>ByteOutputStream</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class GenericLittleEndianWriter implements LittleEndianWriter {
	private static final Charset ASCII_Charset = Charset.forName("US-ASCII");
	private ByteOutputStream bos;

	/**
	 * Creates a <code>LittleEndianWriter</code> wrapping a <code>ByteOutputStream</code>.
	 * @param bos the <code>ByteOutputStream</code> for wrapping
	 */
	public GenericLittleEndianWriter(ByteOutputStream bos) {
		super();
		this.bos = bos;
	}

	/**
	 * Creates an empty <code>LittleEndianWriter</code>.
	 */
	protected GenericLittleEndianWriter() {
		this.bos = null;
	}

	/**
	 * Sets the <code>ByteOutputStream</code> to be used by the writer.
	 * @param bos the <code>ByteOutputStream</code> to be wrapped by the writer
	 */
	protected void setByteOutputStream(ByteOutputStream bos) {
		this.bos = bos;
	}

	@Override
	public void write(byte[] b) {
		for (int x = 0; x < b.length; x++) {
			bos.writeByte(b[x]);
		}
	}

	@Override
	public void write(byte b) {
		bos.writeByte(b);
	}

	public void writeZero(int times) {
		for (int i = 0; i < times; i++)
			bos.writeByte((byte) 0);
	}

	@Override
	public void write(int b) {
		bos.writeByte((byte) b);
	}

	@Override
	public void skip(int b) {
		write(new byte[b]);
	}

	@Override
	public void writeShort(int i) {
		bos.writeByte((byte) (i & 0xFF));
		bos.writeByte((byte) ((i >>> 8) & 0xFF));
	}

	@Override
	public void writeInt(int i) {
		bos.writeByte((byte) (i & 0xFF));
		bos.writeByte((byte) ((i >>> 8) & 0xFF));
		bos.writeByte((byte) ((i >>> 16) & 0xFF));
		bos.writeByte((byte) ((i >>> 24) & 0xFF));
	}

	@Override
	public void writeAsciiString(String s) {
		write(s.getBytes(ASCII_Charset));
	}

	@Override
	public void writeMapleAsciiString(String s) {
		writeShort((short) s.length());
		writeAsciiString(s);
	}

	@Override
	public void writeNullTerminatedAsciiString(String s) {
		writeAsciiString(s);
		write(0);
	}

	@Override
	public void writeLong(long l) {
		bos.writeByte((byte) (l & 0xFF));
		bos.writeByte((byte) ((l >>> 8) & 0xFF));
		bos.writeByte((byte) ((l >>> 16) & 0xFF));
		bos.writeByte((byte) ((l >>> 24) & 0xFF));
		bos.writeByte((byte) ((l >>> 32) & 0xFF));
		bos.writeByte((byte) ((l >>> 40) & 0xFF));
		bos.writeByte((byte) ((l >>> 48) & 0xFF));
		bos.writeByte((byte) ((l >>> 56) & 0xFF));
	}

	@Override
	public void writePos(Point s) {
		writeShort(s.x);
		writeShort(s.y);
	}
}
