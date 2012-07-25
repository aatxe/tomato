package org.tomato.tools.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.opcodes.internal.InternalSendOpcode;

import static org.jboss.netty.buffer.ChannelBuffers.*;

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
	public static ChannelBuffer getKeepAlive() {
		ChannelBuffer buffer = buffer(LITTLE_ENDIAN, 2);
		buffer.writeShort(InternalSendOpcode.AliveReq.getOpcode());
		return buffer;
	}
}
