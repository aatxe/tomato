package org.tomato.event;

import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;

public class KeepAliveEvent extends Event<KeepAliveClient> {
    public KeepAliveEvent(KeepAliveClient sender) {
        super(sender);
    }
}
