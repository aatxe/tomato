package net.server.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * A bootstrapper for internal connections.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalBootstrapper {
	private final ChannelGroup bindings = new DefaultChannelGroup("internal-bindings");
	private ClientBootstrap bootstrap;
	private ChannelFactory factory;
	
	/**
	 * Creates a bootstrapper for internal connections.
	 */
	public InternalBootstrapper() {
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new InternalPipelineFactory());
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
	}
	

	/**
	 * Binds an internal connection to the specified port.
	 * @param port the port to bind the connection
	 */
	public Channel connect(int port) {
		return this.connect(new InetSocketAddress(port));
	}
	
	/**
	 * Binds an internal connection to a specific address and port.
	 * @param address the address to bind the connection, as an IPv4 string.
	 * @param port the port to bind the connection
	 * @throws IOException if the IP address is invalid (not IPv4)
	 */
	public Channel connect(String address, int port) throws IOException {
		String[] ipArray = address.split("\\.");
		byte[] addBytes = new byte[4];
		if (ipArray.length != addBytes.length) {
			throw new IOException("Invalid IP Address. (" + address + ", " + ipArray.length + ")");
		} else {
			for (int i = 0; i < addBytes.length; i++) {
				addBytes[i] = Byte.valueOf(ipArray[i]);
			}
		}
		return this.connect(InetAddress.getByAddress(addBytes), port);
	}
	
	/**
	 * Bind an internal connection to the specified address and port.
	 * @param address the address to bind the connection, as a byte array.
	 * @param port the port to bind the connection
	 * @throws UnknownHostException if the IP address is invalid
	 */
	public void connect(byte[] address, int port) throws UnknownHostException {
		this.connect(InetAddress.getByAddress(address), port);
	}
	
	/**
	 * Binds an internal connection to the specified address and port.
	 * @param address the address to bind the connection
	 * @param port the port to bind the connection
	 */
	public Channel connect(InetAddress address, int port) {
		return this.connect(new InetSocketAddress(address, port));
	}
	
	/**
	 * Binds an internal connection the specified <code>SocketAddress</code>.
	 * @param address the <code>SocketAddress</code> to bind the connection
	 */
	public Channel connect(SocketAddress address) {
		ChannelFuture cf = bootstrap.connect(address);
		cf.awaitUninterruptibly();
		bindings.add(cf.getChannel());
		return cf.getChannel();
	}
	
	/**
	 * Unbinds all internal connections for this bootstrapper.
	 * @return a <code>ChannelGroupFuture</code> representing the unbinding event.
	 */
	public ChannelGroupFuture unbind() {
		return bindings.close();
	}
	
	/**
	 * Unbinds the specified internal connection.
	 * @param address the <code>SocketAddress</code> that the connection is bound to
	 * @return a <code>ChannelFuture</code> representing the unbinding event.
	 */
	public ChannelFuture disconnect(SocketAddress address) {
		for (Channel ch : bindings) {
			if (ch.getLocalAddress().equals(address)) {
				return ch.unbind();
			}
		}
		return null;
	}
	
	/**
	 * Shutdowns all internal connections completely, and frees up resources used by the bootstrapper.
	 */
	public void shutdown() {
		ChannelGroupFuture cgf = this.unbind();
		cgf.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
