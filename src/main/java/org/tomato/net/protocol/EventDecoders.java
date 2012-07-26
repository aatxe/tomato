package org.tomato.net.protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.client.MapleClient;
import org.tomato.client.core.Client;
import org.tomato.client.core.KeepAliveClient;
import org.tomato.event.ClientConnectedEvent;
import org.tomato.event.ClientErrorEvent;
import org.tomato.event.Event;
import org.tomato.event.KeepAliveEvent;
import org.tomato.event.auth.AuthenticationEvent;

import static org.tomato.net.server.opcodes.RecvOpcode.*;
import static org.tomato.net.protocol.Helpers.*;

class EventDecoders {
    @Decode(KeepAlive)
    KeepAliveEvent decodeKeepAlive(KeepAliveClient client, ChannelBuffer buffer) {
        return new KeepAliveEvent(client);
    }

    @Decode(ClientError)
    ClientErrorEvent decodeClientError(Client client, ChannelBuffer buffer) {
        String error = readLengthPrependedString(buffer);
        return new ClientErrorEvent(client, error);
    }

    @Decode(ClientConnected)
    ClientConnectedEvent decodeClientConnected(Client client, ChannelBuffer buffer) {
        return new ClientConnectedEvent(client);
    }
}
