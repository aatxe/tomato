package org.tomato.tools.net;

import org.tomato.net.server.core.MaplePacket;
import org.tomato.net.server.opcodes.internal.InternalRecvOpcode;
import org.tomato.tools.data.output.MaplePacketLittleEndianWriter;

/**
 * A tool for creating <code>MaplePackets</code> for internal use.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalPacketCreator {
	/**
	 * Gets the packet to inform the server that a valid org.tomato.client has connected.
	 * @return the packet to inform the server that a valid org.tomato.client has connected.
	 */
	public static MaplePacket getConnected() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);
		mplew.writeOpcode(InternalRecvOpcode.ClientConnected);
		return mplew.getPacket();
	}
	
	public static MaplePacket getKeepAlive() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);
		mplew.writeOpcode(InternalRecvOpcode.AliveReq);
		return mplew.getPacket();
	}
}
