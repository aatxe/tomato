package org.tomato.net.server.internal;

import org.tomato.net.server.core.MaplePacketProcessor;
import org.tomato.net.server.handlers.internal.KeepAliveHandler;
import org.tomato.net.server.opcodes.internal.InternalSendOpcode;

/**
 * A packet processor for internal connections.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalPacketProcessor implements MaplePacketProcessor {
	private static InternalPacketProcessor instance;
	private InternalPacketHandler handlers[];
	
	/**
	 * Creates a packet processor wrapping an array of handlers.
	 */
	private InternalPacketProcessor() {
		this.reset();
	}
	
	/**
	 * Gets the <code>InternalPacketHandler</code> for the supplied opcode.
	 * @param opcode the opcode for the desired handler
	 * @return the desired <code>InternalPacketHandler</code>
	 */
	public InternalPacketHandler getHandler(int opcode) {
		try {
			return handlers[opcode];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Registers a handler with a specified opcode.
	 * @param opcode the opcode to be registered
	 * @param handler the <code>InternalPacketHandler</code> to be registered
	 */
	public void register(InternalSendOpcode opcode, InternalPacketHandler handler) {
		handlers[opcode.getOpcode()] = handler;
	}
	
	/**
	 * Registers a handler with a specified opcode.
	 * @param opcode the opcode to be registered
	 * @param handler the <code>InternalPacketHandler</code> to be registered
	 */
	public void register(int opcode, InternalPacketHandler handler) {
		handlers[opcode] = handler;
	}

	@Override
	public void reset() {
		int maxOpcode = 0;
		for (InternalSendOpcode opcode : InternalSendOpcode.values()) {
			if (opcode.getOpcode() > maxOpcode) {
				maxOpcode = opcode.getOpcode();
			}
		}
		handlers = new InternalPacketHandler[maxOpcode + 1];
	}

	@Override
	public void configure() {
		this.register(InternalSendOpcode.AliveReq, new KeepAliveHandler());
		// TODO: register some moar handlers.
	}
	
	/**
	 * Gets the universal instance of the packet processor.
	 * @return the universal <code>InternalPacketProcessor</code>
	 */
	public static InternalPacketProcessor getInstance() {
		if (instance == null) {
			instance = new InternalPacketProcessor();
			instance.configure();
		}
		return instance;
	}
}
