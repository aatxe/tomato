package org.tomato.event;

import org.tomato.client.core.Client;

public class ClientDisconnectedEvent extends Event<Client> {
    public ClientDisconnectedEvent(Client sender) {
        super(sender);
    }
}
