package net.server.opcodes;

/**
 * An enumeration of sent opcodes.
 * @author tomato
 * @author XiaoKia
 * @version 1.0
 * @since alpha
 */
public enum SendOpcode {
	// CClientSocket
	MigrateCommand(0x10),
	AliveReq(0x11),
	AuthenCodeChanged(0x12),
	AuthenMessage(0x13),
	CheckCrc(0x1A),
	
	// CLogin
	CheckPassword(0x00),
	GuestIdLogin(0x01),
	AccountInfo(0x02),
	CheckUserLimit(0x03),
	SetAccount(0x04),
	ConfirmEULA(0x05),
	CheckPinCode(0x06),
	UpdatePinCode(0x07),
	ViewAllChar(0x08),
	SelectCharacterByVAC(0x09),
	WorldInformation(0x01),
	WorldSelect(0x0B),
	SelectCharacter(0x0C),
	CheckDuplicatedID(0x0D),
	CreateNewCharacter(0x0E),
	DeleteCharacter(0x0F),
	EnableSPW(0x18),
	LatestConnectedWorld(0x1B),
	RecommendWorldMessage(0x1C),
	ExtraCharInfo(0x1D),
	CheckSPW(0x1F);
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
