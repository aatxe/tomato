package net.server.exec;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * A basic bootstrapper for instances of the server.
 * @author tomato
 * @version 1.0
 * @since 0.1
 */
public class ServerBootstrapper {
	private final ChannelGroup bindings = new DefaultChannelGroup("server-bindings");
	private ChannelFactory factory;
	private ServerBootstrap bootstrap;
	private ChannelPipelineFactory defaultPipelineFactory = new ServerPipelineFactory();
	
	/**
	 * Creates a bootstrapper for the server.
	 */
	public ServerBootstrapper() {
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(defaultPipelineFactory);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
	}
	
	/**
	 * Gets the <code>ChannelPipelineFactory</code> used by the bootstrapper.
	 * @return the pipeline factory in use
	 */
	public ChannelPipelineFactory getPipelineFactory() {
		return this.defaultPipelineFactory;
	}
	
	/**
	 * Sets the <code>ChannelPipelineFactory</code> to be used by the bootstrapper.
	 * @param pipelineFactory the pipeline factory to be used
	 */
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
		this.defaultPipelineFactory = pipelineFactory;
		bootstrap.setPipelineFactory(defaultPipelineFactory);
	}
	
	/**
	 * Binds a login server to the specified port.
	 * @param portNumber the port number to bind
	 */
	public void bind(int portNumber) {
		this.bind(new InetSocketAddress(portNumber));
	}
	
	/**
	 * Binds a login server to the specified port.
	 * @param port the socket address to bind
	 */
	public void bind(SocketAddress port) {
		Channel ch = bootstrap.bind(port);
		bindings.add(ch);
	}
	
	/**
	 * Binds a server from the specified <code>ServerPipelineFactory</code> to the specified port.
	 * @param portNumber the port number to bind
	 * @param pipelineFactory the <code>ServerPipelineFactory</code> to bind from
	 */
	public void bind(int portNumber, ServerPipelineFactory pipelineFactory) {
		this.bind(new InetSocketAddress(portNumber), pipelineFactory);
	}
	

	/**
	 * Binds a server from the specified <code>ServerPipelineFactory</code> to the specified port.
	 * @param port the socket address to bind
	 * @param pipelineFactory the <code>ServerPipelineFactory</code> to bind from
	 */
	public void bind(SocketAddress port, ServerPipelineFactory pipelineFactory) {
		bootstrap.setPipelineFactory(pipelineFactory);
		Channel ch = bootstrap.bind(port);
		bindings.add(ch);
		bootstrap.setPipelineFactory(defaultPipelineFactory);
	}
	
	/**
	 * Unbinds all servers bound to all ports.
	 * @return a ChannelGroupFuture of the unbinding event
	 */
	public ChannelGroupFuture unbind() {
		return bindings.close();
	}
	
	public ChannelFuture unbind(int portNumber) {
		return this.unbind(new InetSocketAddress(portNumber));
	}
	
	public ChannelFuture unbind(SocketAddress port) {
		for (Channel ch : bindings) {
			if (ch.getLocalAddress().equals(port) || ch.getLocalAddress().toString().contains(port.toString())) {
				return ch.unbind();
			}
		}
		return null;
	}
	
	/**
	 * Shutdowns the server entirely.
	 */
	public void shutdown() {
		ChannelGroupFuture cgf = this.unbind();
		cgf.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
