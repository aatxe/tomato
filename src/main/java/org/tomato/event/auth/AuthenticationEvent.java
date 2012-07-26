package org.tomato.event.auth;

import org.tomato.client.core.Client;
import org.tomato.event.Event;

public class AuthenticationEvent extends Event<Client> {
    private final String password;
    private final String username;

    public AuthenticationEvent(Client sender, String username, String password) {
        super(sender);
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
