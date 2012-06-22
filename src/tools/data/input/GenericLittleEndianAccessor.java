package tools.data.input;

import java.awt.Point;
import java.io.ByteArrayOutputStream;

public class GenericLittleEndianAccessor implements LittleEndianAccessor {
	private ByteInputStream bs;

	public GenericLittleEndianAccessor(ByteInputStream bs) {
		this.bs = bs;
	}

	@Override
	public byte readByte() {
		return (byte) bs.readByte();
	}

	@Override
	public int readInt() {
		return bs.readByte() + (bs.readByte() << 8) + (bs.readByte() << 16) + (bs.readByte() << 24);
	}
	@Override
	public short readShort() {
		return (short) (bs.readByte() + (bs.readByte() << 8));
	}

	@Override
	public char readChar() {
		return (char) readShort();
	}
	
	@Override
	public long readLong() {
		long byte1 = bs.readByte();
		long byte2 = bs.readByte();
		long byte3 = bs.readByte();
		long byte4 = bs.readByte();
		long byte5 = bs.readByte();
		long byte6 = bs.readByte();
		long byte7 = bs.readByte();
		long byte8 = bs.readByte();
		return (byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) + (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	public final String readAsciiString(int n) {
		char ret[] = new char[n];
		for (int x = 0; x < n; x++) {
			ret[x] = (char) readByte();
		}
		return String.valueOf(ret);
	}
	
	public final String readNullTerminatedAsciiString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte b;
		while (true) {
			b = readByte();
			if (b == 0) {
				break;
			}
			baos.write(b);
		}
		byte[] buf = baos.toByteArray();
		char[] chrBuf = new char[buf.length];
		for (int x = 0; x < buf.length; x++) {
			chrBuf[x] = (char) buf[x];
		}
		return String.valueOf(chrBuf);
	}

	public long getBytesRead() {
		return bs.getBytesRead();
	}
	
	@Override
	public String readMapleAsciiString() {
		return readAsciiString(readShort());
	}
	
	@Override
	public byte[] read(int num) {
		byte[] ret = new byte[num];
		for (int x = 0; x < num; x++) {
			ret[x] = readByte();
		}
		return ret;
	}

	@Override
	public final Point readPos() {
		final int x = readShort();
		final int y = readShort();
		return new Point(x, y);
	}

	@Override
	public void skip(int num) {
		for (int x = 0; x < num; x++) {
			readByte();
		}
	}

	@Override
	public long available() {
		return bs.available();
	}

	@Override
	public String toString() {
		return bs.toString();
	}
}