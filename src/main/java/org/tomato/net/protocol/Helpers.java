package org.tomato.net.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.charset.Charset;

import static org.jboss.netty.buffer.ChannelBuffers.*;

class Helpers {
    private static final Charset ASCII = Charset.forName("ASCII");

    private Helpers() {}

    public static String readLengthPrependedString(ChannelBuffer buffer) {
        return readLengthPrependedString(buffer, ASCII);
    }

    public static String readLengthPrependedString(ChannelBuffer buffer, Charset charset) {
        return buffer.readBytes(buffer.readUnsignedShort()).toString(charset);
    }

    public static ChannelBuffer lengthPrependedString(String string) {
        ChannelBuffer buffer = buffer(LITTLE_ENDIAN, string.length() + 2);
        buffer.writeShort(string.length());
        buffer.writeBytes(copiedBuffer(LITTLE_ENDIAN, string, ASCII));
        return buffer;
    }
}
