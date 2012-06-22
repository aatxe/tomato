package tools.data.input;

import java.awt.Point;

public interface LittleEndianAccessor {
	public byte readByte();
	public char readChar();
	public short readShort();
	public int readInt();
	public Point readPos();
	public long readLong();
	public void skip(int num);
	public byte[] read(int num);
	public float readFloat();
	public double readDouble();
	public String readAsciiString(int n);
	public String readNullTerminatedAsciiString();
	public String readMapleAsciiString();
	public long getBytesRead();
	public long available();
}
