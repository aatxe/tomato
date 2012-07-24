package org.tomato.net.server.encryption;

import org.tomato.net.encryption.MapleObfuscator;
import org.tomato.net.server.core.ByteArrayMaplePacket;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.tomato.tools.ConsoleOutput;
import org.tomato.tools.HexTool;
import org.tomato.client.core.CryptoClient;

/**
 * A one-to-one decoder for converting an encrypted <code>ChannelBuffer</code> to a <code>MaplePacket</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MaplePacketDecoder extends OneToOneDecoder {
	@Override
	public Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		CryptoClient client = (CryptoClient) channel.getAttachment();
		ChannelBuffer cb = (ChannelBuffer) msg;
		int packetLength = -1;
		ConsoleOutput.print("[Encrypted] " + HexTool.toString(cb.array()));
		if (cb.readableBytes() < 4) {
			return null;
		} else {
			int encodedLength = cb.readInt();
			if (!client.getRecvCrypto().checkPacket(encodedLength)) {
				channel.close();
				return null;
			}
			packetLength = MapleObfuscator.getPacketLength(encodedLength);
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
