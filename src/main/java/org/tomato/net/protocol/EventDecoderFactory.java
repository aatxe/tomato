package org.tomato.net.protocol;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventDecoderFactory {
    public static EventDecoder getEventDecoder(Class<?> decoders) {
        Map<Integer, Method> methods = new HashMap<Integer, Method>();
        for(Method method : decoders.getMethods()) {
            if(method.isAnnotationPresent(Decode.class)) {
                Decode annotation = method.getAnnotation(Decode.class);
                methods.put(annotation.value().getOpcode(), method);
            }
        }
        return new EventDecoder(methods);
    }
}
