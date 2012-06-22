package net.server.encryption;

import net.server.core.MaplePacket;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import client.MapleClient;

public class MaplePacketEncoder extends OneToOneEncoder {
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		MaplePacket packet = (MaplePacket) msg;
		MapleClient client = (MapleClient) channel.getAttachment();
		if (client != null) {
			final byte[] input = packet.getBytes();
			final byte[] unencrypted = new byte[input.length];
			System.arraycopy(input, 0, unencrypted, 0, input.length);
			final byte[] ret = new byte[unencrypted.length + 4];
			final byte[] header = client.getSendCrypto().getPacketHeader(unencrypted.length);
			MapleEncryption.encryptData(unencrypted);
			synchronized (client.getSendCrypto()) {
				client.getSendCrypto().crypt(unencrypted);
				System.arraycopy(header, 0, ret, 0, 4);
				System.arraycopy(unencrypted, 0, ret, 4, unencrypted.length);
				return ChannelBuffers.wrappedBuffer(ChannelBuffers.LITTLE_ENDIAN, ret);
			}
		} else {
			return ChannelBuffers.wrappedBuffer(ChannelBuffers.LITTLE_ENDIAN, packet.getBytes());
		}
	}
}
