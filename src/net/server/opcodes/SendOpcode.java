package net.server.opcodes;

public enum SendOpcode {
	TROLL(0x00);
	
	private int opcode = 0;
	
	private SendOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	public int getOpcode() {
		return opcode;
	}
}
