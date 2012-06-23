package net.server.opcodes;

public enum RecvOpcode {
	TROLL(0x00);
	
	private int opcode = 0;
	
	private RecvOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	public int getOpcode() {
		return opcode;
	}
}
