package org.tomato.net.server;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;

public interface Server {
	/**
	 * Gets the pipeline factory used by the bootstrapper.
	 * @return the pipeline factory in use by the bootstrapper
	 */
	public ChannelPipelineFactory getPipelineFactory();
	
	/**
	 * Sets the pipeline factory to be used by the bootstrapper.
	 * @param pipelineFactory the pipeline factory to be used by the bootstrapper
	 */
	public void setPipelineFactory(ChannelPipelineFactory pipelineFactory);
	
	/**
	 * Binds the server to the specified port.
	 * @param port the port to bind the server to
	 * @return a <code>Channel</code> representing the newly bound server
	 */
	public Channel bind(int port);
	
	/**
	 * Unbinds the currently-bound server.
	 * @return a <code>ChannelFuture</code> representing the unbinding event
	 */
	public ChannelFuture unbind();
	
	/**
	 * Gets the current server's binding.
	 */
	public Channel getBinding();
	
	/**
	 * Shutdowns the server completely, freeing up all used resources.
	 */
	public void shutdown();
}
