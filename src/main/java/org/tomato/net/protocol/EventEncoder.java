package org.tomato.net.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.tomato.client.core.CryptoClient;
import org.tomato.event.Event;

import java.lang.reflect.Method;
import java.util.Map;

public class EventEncoder extends OneToOneEncoder {
    private Map<Class<Event>, Method> encoders;

    public EventEncoder(Map<Class<Event>, Method> encoders) {
        this.encoders = encoders;
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        Event event;
        if(message instanceof Event) {
            event = (Event) message;
        } else {
            return message;
        }

        CryptoClient client = (CryptoClient) ctx.getAttachment();

        if(encoders.containsKey(event.getClass())) {
            Method encoder = encoders.get(event.getClass());
            ChannelBuffer buffer = (ChannelBuffer) encoder.invoke(null, event);
            return buffer;
        } else {
            throw new RuntimeException("Unknown event type");
        }
    }
}
