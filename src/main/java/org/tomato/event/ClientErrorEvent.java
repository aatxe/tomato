package org.tomato.event;

import org.tomato.client.core.Client;

public class ClientErrorEvent extends Event<Client> {
    private final String message;

    public ClientErrorEvent(Client sender, String message) {
        super(sender);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Throwable asThrowable() {
        return new Exception(message);
    }
}
