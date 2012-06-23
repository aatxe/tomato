package net.server.opcodes;

/**
 * An enumeration of sent opcodes.
 * @author tomato
 * @author XiaoKia
 * @version 1.0
 * @since alpha
 */
public enum SendOpcode {
	TROLL(0x00);
	private int opcode = 0;

	/**
	 * Creates a new opcode.
	 * @param opcode the value of the opcode
	 */
	private SendOpcode(int opcode) {
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
