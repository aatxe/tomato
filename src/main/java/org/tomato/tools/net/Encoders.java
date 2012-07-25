package org.tomato.tools.net;

import java.nio.charset.Charset;
import org.jboss.netty.buffer.ChannelBuffer;

import static org.jboss.netty.buffer.ChannelBuffers.*;

public class Encoders {
    private static Charset ASCII = Charset.forName("ASCII");
    
    private Encoders() {}
    
    public static ChannelBuffer mapleAsciiString(String input) {
        ChannelBuffer buffer = buffer(LITTLE_ENDIAN, input.length() + 2);
        buffer.writeShort(input.length());
        buffer.writeBytes(copiedBuffer(LITTLE_ENDIAN, input, ASCII));
        return buffer;
    }
}
