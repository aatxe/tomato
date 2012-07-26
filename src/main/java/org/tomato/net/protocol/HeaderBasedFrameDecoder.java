package org.tomato.net.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.client.core.CryptoClient;
import org.tomato.net.encryption.MapleObfuscator;
import org.tomato.net.server.encryption.MapleEncryption;

import static org.jboss.netty.buffer.ChannelBuffers.*;

public class HeaderBasedFrameDecoder extends FrameDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderBasedFrameDecoder.class);

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        CryptoClient client = (CryptoClient) ctx.getAttachment();
        if(buffer.readableBytes() < 4) {
            return null;
        }

        buffer.markReaderIndex();

        int header = buffer.readInt();
        if(!client.getRecvCrypto().checkPacket(header)) {
            buffer.resetReaderIndex();
            throw new CorruptedFrameException("Invalid packet header");
        }

        int length = MapleObfuscator.getPacketLength(header);
        if(buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return null;
        }

        byte[] frame = buffer.readBytes(length).array();
        client.getRecvCrypto().crypt(frame);
        MapleEncryption.decryptData(frame);
        return wrappedBuffer(LITTLE_ENDIAN, frame);
    }
}
