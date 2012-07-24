package org.tomato.net.server.opcodes;

/**
 * An enumeration of recieved opcodes.
 * @author tomato
 * @author XiaoKia
 * @version 1.0
 * @since alpha
 */
public enum RecvOpcode {
	ClientValidation(0x14),
	KeepAlive(0x2E),
	ClientError(0x2F),
	ClientConnected(0x38);
	private int opcode = 0;
	
	/**
	 * Creates a new opcode.
	 * @param opcode the value of the opcode
	 */
	private RecvOpcode(int opcode) {
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
