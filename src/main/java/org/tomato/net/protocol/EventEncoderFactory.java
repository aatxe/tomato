package org.tomato.net.protocol;


import org.tomato.event.Event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Refactor with EventDecoderFactory -> finding strategies
 */
public class EventEncoderFactory {
    public static EventEncoder getEventDecoder(Class<?> encoders) {
        Map<Class<Event>, Method> methods = new HashMap<Class<Event>, Method>();
        for(Method method : encoders.getMethods()) {
            if(method.isAnnotationPresent(Encode.class)) {
                Encode annotation = method.getAnnotation(Encode.class);
                methods.put(annotation.value(), method);
            }
        }
        return new EventEncoder(methods);
    }
}
