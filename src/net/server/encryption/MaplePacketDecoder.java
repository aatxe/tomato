package net.server.encryption;

import net.encryption.MapleObfuscator;
import net.server.core.ByteArrayMaplePacket;
import net.server.opcodes.RecvOpcode;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import client.MapleClient;

/**
 * A one-to-one decoder for converting an encrypted <code>ChannelBuffer</code> to a <code>MaplePacket</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MaplePacketDecoder extends OneToOneDecoder {
	@Override
	public Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		MapleClient client = (MapleClient) channel.getAttachment();
		ChannelBuffer cb = (ChannelBuffer) msg;
		int packetLength = -1;
		if (cb.readableBytes() == 2 && (cb.readByte() == RecvOpcode.ClientConnected.getOpcode() || cb.readByte() == RecvOpcode.ClientConnected.getOpcode())) {
			return new ByteArrayMaplePacket(cb.array());
		} else if (cb.readableBytes() < 4) {
			return null;
		} else {
			int opcode = cb.readInt();
			if (!client.getRecvCrypto().checkPacket(opcode)) {
				channel.close();
				return null;
			}
			packetLength = MapleObfuscator.getPacketLength(opcode);
		}
		if (cb.readableBytes() >= packetLength) {
			byte decryptedPacket[] = cb.readBytes(packetLength).array();
			client.getRecvCrypto().crypt(decryptedPacket);
			MapleEncryption.decryptData(decryptedPacket);
			return new ByteArrayMaplePacket(decryptedPacket);
		}
		return null;
	}
}
