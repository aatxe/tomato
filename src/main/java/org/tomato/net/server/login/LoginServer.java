package org.tomato.net.server.login;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.tomato.net.server.AbstractServer;
import org.tomato.net.server.internal.InternalBootstrapper;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * A server for handling new clients, and login actions.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServer extends AbstractServer {
	private InternalBootstrapper internalBootstrap;
	
	/**
	 * Creates a new login server that is unbound.
	 */
	public LoginServer() {
		super();
		pipelineFactory = new LoginServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);
		internalBootstrap = new InternalBootstrapper();
	}
	
	/**
	 * Creates a new login server bound to the specified port.
	 * @param port the port to bind the login server to.
	 */
	public LoginServer(int port) {
		this();
		binding = this.bind(port);
	}
	
	/**
	 * Connects the login server internally to another server.
	 * @param address the address to connect to
	 * @param port the port to connect to
	 * @return a <code>Channel</code> representing the new internal communication
	 * @throws IOException if the IP address is invalid (not IPv4)
	 */
	public Channel connect(String address, int port) throws IOException {
		return internalBootstrap.connect(address, port);
	}

	/**
	 * Disconnects the login server from the specified connection.
	 * @param address the address to disconnect from
	 * @param port the port to disconnect from
	 * @return a <code>ChannelFuture</code> representing the unbinding event
	 * @throws IOException if the IP address is invalid (not IPv4)
	 */
	public ChannelFuture disconnect(String address, int port) throws IOException {
		String[] ipArray = address.split("\\.");
		byte[] addBytes = new byte[4];
		if (ipArray.length != addBytes.length) {
			throw new IOException("Invalid IP Address.");
		} else {
			for (int i = 0; i < addBytes.length; i++) {
				addBytes[i] = Byte.valueOf(ipArray[i]);
			}
		}
		return internalBootstrap.disconnect(new InetSocketAddress(InetAddress.getByAddress(addBytes), port));
	}
}
