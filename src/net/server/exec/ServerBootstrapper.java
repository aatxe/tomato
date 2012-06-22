package net.server.exec;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class ServerBootstrapper {
	private final ChannelGroup bindings = new DefaultChannelGroup("server-bindings");
	private ChannelFactory factory;
	private ServerBootstrap bootstrap;
	private ServerPipelineFactory defaultPipelineFactory = new ServerPipelineFactory();
	
	public ServerBootstrapper() {
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(defaultPipelineFactory);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
	}
	
	public void bind(int portNumber) {
		this.bind(new InetSocketAddress(portNumber));
	}
	
	public void bind(SocketAddress port) {
		Channel ch = bootstrap.bind(port);
		bindings.add(ch);
	}
	
	public void bind(int portNumber, ChannelPipelineFactory pipelineFactory) {
		this.bind(new InetSocketAddress(portNumber), pipelineFactory);
	}
	
	public void bind(SocketAddress port, ChannelPipelineFactory pipelineFactory) {
		bootstrap.setPipelineFactory(pipelineFactory);
		Channel ch = bootstrap.bind(port);
		bindings.add(ch);
		bootstrap.setPipelineFactory(defaultPipelineFactory);
	}
	
	public ChannelGroupFuture unbind() {
		return bindings.close();
	}
	
	public void shutdown() {
		ChannelGroupFuture cgf = this.unbind();
		cgf.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
