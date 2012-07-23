package net.server.world;

import net.server.AbstractServer;

/**
 * A server for handling cross-channel actions, and migrations.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldServer extends AbstractServer {
	/**
	 * Creates a new world server that is unbound.
	 */
	public WorldServer() {
		super();
		pipelineFactory = new WorldServerPipelineFactory();
		bootstrap.setPipelineFactory(pipelineFactory);
	}
	
	/**
	 * Creates a new world server bound to a port.
	 * @param port the port to bind the world server to
	 */
	public WorldServer(int port) {
		this();
		this.bind(port);
	}
}
