package net.server.login;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import net.server.internal.InternalBootstrapper;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * A server for handling new clients, and login actions.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServer {
	private Channel binding;
	private ChannelPipelineFactory pipelineFactory;
	private ChannelFactory factory;
	private InternalBootstrapper internalBootstrap;
	private ServerBootstrap bootstrap;
	
	/**
	 * Creates a new login server that is unbound.
	 */
	public LoginServer() {
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		bootstrap = new ServerBootstrap(factory);
		pipelineFactory = new LoginServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
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
	 * Gets the pipeline factory used by the bootstrapper.
	 * @return the pipeline factory in use by the bootstrapper
	 */
	public ChannelPipelineFactory getPipelineFactory() {
		return pipelineFactory;
	}
	
	/**
	 * Sets the pipeline factory to be used by the bootstrapper.
	 * @param pipelineFactory the pipeline factory to be used by the bootstrapper
	 */
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
		this.pipelineFactory = pipelineFactory;
		bootstrap.setPipelineFactory(pipelineFactory);
	}
	
	/**
	 * Binds the login server to the specified port.
	 * @param port the port to bind the login server to
	 * @return a <code>Channel</code> representing the newly bound server
	 */
	public Channel bind(int port) {
		Channel ch = bootstrap.bind(new InetSocketAddress(port));
		binding = ch;
		return ch;
	}

	/**
	 * Unbinds the currently-bound login server.
	 * @return a <code>ChannelFuture</code> representing the unbinding event
	 */
	public ChannelFuture unbind() {
		return binding.unbind();
	}

	/**
	 * Connects the login server internally to another server.
	 * @param address the address to connect to
	 * @param port the port to connect to
	 * @return a <code>Channel</code> representing the new internal communication
	 * @throws IOException if the IP address is invalid (not IPv4)
	 */
	public Channel connect(String address, int port) throws IOException {
		return internalBootstrap.bind(address, port);
	}

	/**
	 * Disconnects the login server from the specified connection.
	 * @param address the address to disconnect from
	 * @param port the port to disconnect from
	 * @return a <code>ChannelFuture</code> representing the unbinding event
	 * @throws IOException if the IP address is invalid (not IPv4)
	 */
	public ChannelFuture disconnect(String address, int port) throws IOException {
		String[] ipArray = address.split(".");
		byte[] addBytes = new byte[4];
		if (ipArray.length != addBytes.length) {
			throw new IOException("Invalid IP Address.");
		} else {
			for (int i = 0; i < addBytes.length; i++) {
				addBytes[i] = Byte.valueOf(ipArray[i]);
			}
		}
		return internalBootstrap.unbind(new InetSocketAddress(InetAddress.getByAddress(addBytes), port));
	}
	
	/**
	 * Shutdowns the login server completely, freeing up all used resources.
	 */
	public void shutdown() {
		ChannelFuture cf = this.unbind();
		cf.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
