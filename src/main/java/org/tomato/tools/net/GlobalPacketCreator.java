package org.tomato.tools.net;

import org.tomato.net.server.core.MaplePacket;
import org.tomato.net.server.opcodes.SendOpcode;
import org.tomato.tools.data.output.MaplePacketLittleEndianWriter;
import org.tomato.constants.ServerConstants;

public class GlobalPacketCreator {
	/**
	 * Gets the basic introduction packet to send to new clients.
	 * @param majorVersion the major version number
	 * @param minorVersion the minor version number
	 * @param sendIv the initialization vector for sent packets
	 * @param recvIv the initialization vector for received packets
	 * @return the basic introduction packet to send to new clients
	 */
	public static MaplePacket getHandshake(short majorVersion, short minorVersion, byte[] sendIv, byte[] recvIv) {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(16);
		mplew.writeShort(0x0E);
		mplew.writeShort(majorVersion);
		mplew.writeShort(minorVersion);
		mplew.write(0x31); // Unknown
		mplew.write(recvIv);
		mplew.write(sendIv);
		mplew.write(ServerConstants.REGION_CODE);
		return mplew.getPacket();
	}
	
	/**
	 * Gets the packet to trigger a KeepAlive event with a org.tomato.client.
	 * @return the packet to trigger a KeepAlive event with a org.tomato.client
	 */
	public static MaplePacket getKeepAlive() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);
		mplew.writeOpcode(SendOpcode.AliveReq);
		return mplew.getPacket();
	}
}
