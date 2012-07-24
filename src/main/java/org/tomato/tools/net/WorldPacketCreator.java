package org.tomato.tools.net;

import org.tomato.net.server.core.MaplePacket;
import org.tomato.net.server.opcodes.internal.InternalSendOpcode;
import org.tomato.tools.data.output.MaplePacketLittleEndianWriter;

/**
 * A tool for creating <code>MaplePackets</code> for world.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldPacketCreator {
	/**
	 * Gets the packet to trigger a KeepAlive event with a org.tomato.client.
	 * @return the packet to trigger a KeepAlive event with a org.tomato.client
	 */
	public static MaplePacket getKeepAlive() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(2);
		mplew.writeOpcode(InternalSendOpcode.AliveReq);
		return mplew.getPacket();
	}
}
