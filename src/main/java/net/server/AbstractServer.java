package net.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public abstract class AbstractServer implements Server {
	protected Channel binding;
	protected ChannelPipelineFactory pipelineFactory;
	protected ChannelFactory factory;
	protected ServerBootstrap bootstrap;
	
	/**
	 * Creates a new server that is unbound.
	 */
	public AbstractServer() {
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		bootstrap = new ServerBootstrap(factory);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
	}
	
	@Override
	public ChannelPipelineFactory getPipelineFactory() {
		return pipelineFactory;
	}
	
	@Override
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory) {
		this.pipelineFactory = pipelineFactory;
		bootstrap.setPipelineFactory(pipelineFactory);
	}
	
	@Override
	public Channel bind(int port) {
		Channel ch = bootstrap.bind(new InetSocketAddress(port));
		binding = ch;
		return ch;
	}
	
	@Override
	public ChannelFuture unbind() {
		return binding.unbind();
	}
	
	@Override
	public Channel getBinding() {
		return binding;
	}
	
	@Override
	public void shutdown() {
		ChannelFuture cf = this.unbind();
		cf.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
