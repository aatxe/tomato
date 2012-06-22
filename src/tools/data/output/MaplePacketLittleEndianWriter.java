package tools.data.output;

import java.io.ByteArrayOutputStream;
import net.server.core.ByteArrayMaplePacket;
import net.server.core.MaplePacket;
import tools.HexTool;

public class MaplePacketLittleEndianWriter extends GenericLittleEndianWriter {
	private ByteArrayOutputStream baos;

	public MaplePacketLittleEndianWriter() {
		this(32);
	}

	public MaplePacketLittleEndianWriter(int size) {
		this.baos = new ByteArrayOutputStream(size);
		this.setByteOutputStream(new BAOSByteOutputStream(baos));
	}
	
	public MaplePacket getPacket() {
		return new ByteArrayMaplePacket(baos.toByteArray());
	}

	@Override
	public String toString() {
		return HexTool.toString(baos.toByteArray());
	}
}
