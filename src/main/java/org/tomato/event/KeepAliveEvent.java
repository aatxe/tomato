package org.tomato.event;

import org.tomato.client.core.Client;

public class KeepAliveEvent extends Event<Client> {
    public KeepAliveEvent(Client sender) {
        super(sender);
    }
}
