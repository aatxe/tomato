package net.tools;

import net.server.core.MaplePacket;
import tools.data.output.MaplePacketLittleEndianWriter;

/**
 * A tool for creating <code>MaplePackets</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MaplePacketCreator {
	/**
	 * Gets the basic introduction packet to send to new clients.
	 * @param majorVersion the major version number
	 * @param minorVersion the minor version number
	 * @param sendIv the initialization vector for sent packets
	 * @param recvIv the initialization vector for received packets
	 * @return the basic introduction packet to send to new clients
	 */
	public static MaplePacket getHandshake(short majorVersion, short minorVersion, byte[] sendIv, byte[] recvIv) {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeShort(0x0E);
		mplew.writeShort(majorVersion);
		mplew.writeShort(minorVersion);
		mplew.write(0x31); // Unknown
		mplew.write(recvIv);
		mplew.write(sendIv);
		mplew.write(0x08);
		return mplew.getPacket();
	}
	
	/**
	 * Gets the packet for a failed login due to an incorrect password.
	 * @return the packet for a failed login due to an incorrect password
	 */
	public static MaplePacket getLoginFailed() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeShort(0x00);
		mplew.writeInt(4); // reason
		// TODO: make this more extensible.
		mplew.writeShort(0);
		return mplew.getPacket();
	}
}
