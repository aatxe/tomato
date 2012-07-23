package net.server.world;

import net.server.core.AbstractMaplePacketProcessor;
import net.server.core.MaplePacketHandler;
import net.server.handlers.world.ServerClientConnectedHandler;
import net.server.opcodes.internal.InternalRecvOpcode;

/**
 * A packet processor to get handlers for packets to the world server based on opcode.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldPacketProcessor extends AbstractMaplePacketProcessor {
	private static WorldPacketProcessor instance;
	
	/**
	 * Creates a packet processor for internal packets to the world server.
	 */
	private WorldPacketProcessor() {
		this.reset();
	}

	/**
	 * Registers a handler with a specified opcode.
	 * @param opcode the opcode to be registered
	 * @param handler the <code>MaplePacketHandler</code> to be registered
	 */
	public void register(InternalRecvOpcode opcode, MaplePacketHandler handler) {
		handlers[opcode.getOpcode()] = handler;
	}

	@Override
	public void configure() {
		register(InternalRecvOpcode.ClientConnected, new ServerClientConnectedHandler());
		// TODO: register moar handlers
	}
	
	/**
	 * Gets the universal instance of the packet processor.
	 * @return the universal <code>WorldPacketProcessor</code>
	 */
	public static WorldPacketProcessor getInstance() {
		if (instance == null) {
			instance = new WorldPacketProcessor();
			instance.configure();
		}
		return instance;
	}
}
