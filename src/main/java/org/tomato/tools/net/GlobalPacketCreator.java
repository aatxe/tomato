package org.tomato.tools.net;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.tomato.net.server.opcodes.SendOpcode;
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
	public static ChannelBuffer getHandshake(short majorVersion, short minorVersion, byte[] sendIv, byte[] recvIv) {
		ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 16);
		buffer.writeShort(0x0E);
		buffer.writeShort(majorVersion);
		buffer.writeShort(minorVersion);
		buffer.writeByte(0x31); // Unknown
		buffer.writeBytes(recvIv);
		buffer.writeBytes(sendIv);
		buffer.writeByte(ServerConstants.REGION_CODE);
		return buffer;
	}
	
	/**
	 * Gets the packet to trigger a KeepAlive event with a org.tomato.client.
	 * @return the packet to trigger a KeepAlive event with a org.tomato.client
	 */
	public static ChannelBuffer getKeepAlive() {
		ChannelBuffer buffer = ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 2);
		buffer.writeShort(SendOpcode.AliveReq.getOpcode());
		return buffer;
	}
}
