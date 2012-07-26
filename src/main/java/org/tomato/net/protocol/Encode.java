package org.tomato.net.protocol;

import org.tomato.event.Event;
import org.tomato.net.server.opcodes.SendOpcode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Encode {
    public Class<Event> value();
}
