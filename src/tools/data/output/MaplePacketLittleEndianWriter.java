package tools.data.output;

import java.io.ByteArrayOutputStream;
import net.server.core.ByteArrayMaplePacket;
import net.server.core.MaplePacket;
import net.server.opcodes.SendOpcode;
import tools.HexTool;

/**
 * A <code>LittleEndianWriter</code> for <code>MaplePackets</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MaplePacketLittleEndianWriter extends GenericLittleEndianWriter {
	private ByteArrayOutputStream baos;

	/**
	 * Creates a <code>LittleEndianWriter</code> for <code>MaplePackets</code> with a capacity of 32.
	 */
	public MaplePacketLittleEndianWriter() {
		this(32);
	}
	
	/**
	 * Creates a <code>LittleEndianWriter</code> for <code>MaplePackets</code>.
	 */
	public MaplePacketLittleEndianWriter(int size) {
		this.baos = new ByteArrayOutputStream(size);
		this.setByteOutputStream(new BAOSByteOutputStream(baos));
	}
	
	/**
	 * Gets the packet from the writer.
	 * @return the newly created <code>MaplePacket</code>
	 */
	public MaplePacket getPacket() {
		return new ByteArrayMaplePacket(baos.toByteArray());
	}
	
	/**
	 * Write an opcode as a short to the sequence.
	 * @param opcode the opcode to write
	 */
	public void writeOpcode(SendOpcode opcode) {
		this.writeShort(opcode.getOpcode());
	}

	@Override
	public String toString() {
		return HexTool.toString(baos.toByteArray());
	}
}
