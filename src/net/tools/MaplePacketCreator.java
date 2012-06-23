package net.tools;

import net.server.core.MaplePacket;
import tools.data.output.MaplePacketLittleEndianWriter;

public class MaplePacketCreator {
	public static MaplePacket getHello(short majorVersion, short minorVersion, byte[] sendIv, byte[] recvIv) {
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
	
	public static MaplePacket getLoginFailed() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeShort(0x00);
		mplew.writeInt(4);
		mplew.writeShort(0);
		return mplew.getPacket();
	}
}
