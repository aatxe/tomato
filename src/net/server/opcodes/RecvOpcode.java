package net.server.opcodes;

/**
 * An enumeration of recieved opcodes.
 * @author tomato
 * @author XiaoKia
 * @version 1.0
 * @since alpha
 */
public enum RecvOpcode {
	KeepAlive(0x2E);
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
