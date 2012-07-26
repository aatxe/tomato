package org.tomato.event;

public abstract class Event<T> {
    private final T sender;
    
    public Event(T sender) {
        this.sender = sender;
    }

    public T getSender() {
        return sender;
    }
}
