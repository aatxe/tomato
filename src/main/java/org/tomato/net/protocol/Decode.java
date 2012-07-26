package org.tomato.net.protocol;

import org.tomato.net.server.opcodes.RecvOpcode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Decode {
    public RecvOpcode value();
}
