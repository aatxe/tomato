package net.server.core;

import net.server.opcodes.RecvOpcode;

public abstract class AbstractMaplePacketProcessor implements MaplePacketProcessor {
	protected MaplePacketHandler[] handlers;
	
	/**
	 * Gets the <code>MaplePacketHandler</code> for the supplied opcode.
	 * @param opcode the opcode for the desired handler
	 * @return the desired <code>MaplePacketHandler</code>
	 */
	public MaplePacketHandler getHandler(int opcode) {
		try {
			return handlers[opcode];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Registers a handler with a specified opcode.
	 * @param opcode the opcode to be registered
	 * @param handler the <code>MaplePacketHandler</code> to be registered
	 */
	public void register(int opcode, MaplePacketHandler handler) {
		handlers[opcode] = handler;
	}

	@Override
	public void reset() {
		int maxOpcode = 0;
		for (RecvOpcode opcode : RecvOpcode.values()) {
			if (opcode.getOpcode() > maxOpcode) {
				maxOpcode = opcode.getOpcode();
			}
		}
		handlers = new MaplePacketHandler[maxOpcode + 1];
	}
}
