package org.tomato.event;

import org.tomato.client.core.Client;

public class ClientConnectedEvent extends Event<Client> {
    public ClientConnectedEvent(Client sender) {
        super(sender);
    }
}
