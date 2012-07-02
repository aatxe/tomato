package net.server.login;

import net.server.core.AbstractMaplePacketProcessor;
import net.server.core.MaplePacketHandler;
import net.server.handlers.ClientConnectedHandler;
import net.server.handlers.ClientErrorHandler;
import net.server.handlers.KeepAliveHandler;
import net.server.opcodes.RecvOpcode;

/**
 * A processor to get handlers for login based on opcode.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginPacketProcessor extends AbstractMaplePacketProcessor {
	private static LoginPacketProcessor instance;
	
	/**
	 * Creates a packet processor wrapping an array of handlers.
	 */
	private LoginPacketProcessor() {
		this.reset();
	}

	/**
	 * Registers a handler with a specified opcode.
	 * @param opcode the opcode to be registered
	 * @param handler the <code>MaplePacketHandler</code> to be registered
	 */
	public void register(RecvOpcode opcode, MaplePacketHandler handler) {
		handlers[opcode.getOpcode()] = handler;
	}

	@Override
	public void configure() {
		register(RecvOpcode.ClientConnected, new ClientConnectedHandler());
		register(RecvOpcode.KeepAlive, new KeepAliveHandler());
		register(RecvOpcode.ClientError, new ClientErrorHandler());
		// TODO: register moar handlers, yo.
	}

	/**
	 * Gets the universal instance of the packet processor.
	 * @return the universal <code>LoginPacketProcessor</code>
	 */
	public static LoginPacketProcessor getInstance() {
		if (instance == null) {
			instance = new LoginPacketProcessor();
			instance.configure();
		}
		return instance;
	}
}
