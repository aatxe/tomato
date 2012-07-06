package net.server.opcodes.internal;

/**
 * An enumeration of internally recieved opcodes.
 * @author tomato
 * @author XiaoKia
 * @version 1.0
 * @since alpha
 */
public enum InternalRecvOpcode {
	ClientConnected(0x38);
	private int opcode = 0;
	
	/**
	 * Creates a new opcode.
	 * @param opcode the value of the opcode
	 */
	private InternalRecvOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	/**
	 * Gets the value of the opcode.
	 * @return the value of the opcode
	 */
	public int getOpcode() {
		return opcode;
	}
}
