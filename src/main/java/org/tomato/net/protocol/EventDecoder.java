package org.tomato.net.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.tomato.client.MapleClient;

import java.lang.reflect.Method;
import java.util.Map;

public class EventDecoder extends OneToOneDecoder {
    private final Map<Integer,Method> decoders;

    public EventDecoder(Map<Integer,Method> decoders) {
        this.decoders = decoders;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        ChannelBuffer buffer = (ChannelBuffer) message;
        MapleClient client = (MapleClient) ctx.getAttachment();
        int opcode = buffer.readShort();
        if(decoders.containsKey(opcode)) {
            return decoders.get(opcode).invoke(null, client, buffer);
        } else {
            throw new RuntimeException("Unknown opcode");
        }
    }
}
