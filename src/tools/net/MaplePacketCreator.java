package tools.net;

import constants.ServerConstants;
import net.server.core.MaplePacket;
import net.server.opcodes.SendOpcode;
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
		mplew.write(ServerConstants.REGION_CODE);
		return mplew.getPacket();
	}
	
	/**
	 * Gets the packet to display a login failed message.
	 * See packet documentation on CLogin::OnCheckPasswordResult for <code>reason</code>.
	 * @param reason the reason the login failed
	 * @return the packet to display a login failed message.
	 */
	public static MaplePacket getLoginFailed(byte reason) {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeOpcode(SendOpcode.CheckPassword);
		mplew.write(reason);
		mplew.write(0x00);
		mplew.writeInt(0x00);
		return mplew.getPacket();
	}
	
	/**
	 * Gets the packet to trigger a KeepAlive event with a client.
	 * @return the packet to trigger a KeepAlive event with a client
	 */
	public static MaplePacket getKeepAlive() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);
		mplew.writeOpcode(SendOpcode.AliveReq);
		return mplew.getPacket();
	}
}
