package org.tomato.tools.net;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.tomato.net.server.opcodes.internal.InternalRecvOpcode;

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
	public static ChannelBuffer getConnected() {
		ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 2);
		buffer.writeShort(InternalRecvOpcode.ClientConnected.getOpcode());
		return buffer;
	}
	
	public static ChannelBuffer getKeepAlive() {
		ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 2);
		buffer.writeShort(InternalRecvOpcode.AliveReq.getOpcode());
		return buffer;
	}
}
