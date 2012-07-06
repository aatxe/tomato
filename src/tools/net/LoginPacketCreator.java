package tools.net;

import net.server.core.MaplePacket;
import net.server.opcodes.SendOpcode;
import tools.TimeTool;
import tools.data.output.MaplePacketLittleEndianWriter;
import client.MapleAccount;

/**
 * A tool for creating <code>MaplePackets</code> for login.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class LoginPacketCreator {	
	/**
	 * Gets the packet for a successful login action.
	 * See packet documentation on CLogin::OnCheckPasswordResult for more information.
	 * @param account the account of the user logging in
	 * @return the packet for a successful login action
	 */
	public static MaplePacket getLoginSuccess(MapleAccount account) {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeOpcode(SendOpcode.CheckPassword);
		mplew.write(0x00);
		mplew.write(0x01);
		mplew.writeInt(0x00);
		mplew.writeInt(account.getAccountId());
		mplew.write(account.getGender());
		mplew.write((account.isGM()) ? 0x01 : 0x00);
		mplew.writeShort((account.isGM()) ? 0x80 : 0x00);
		mplew.write(0x00);
		mplew.writeMapleAsciiString(account.getAccountName());
		mplew.write(0x00);
		mplew.write((account.isTradeBlocked()) ? 0x01 : 0x00);
		mplew.writeLong(TimeTool.unixToFiletime(account.getUnixTradeBlockExpiration()));
		mplew.write(0x00);
		mplew.writeLong(TimeTool.unixToFiletime(account.getUnixCreationDate()));
		mplew.writeInt(0x0C);
		mplew.write(0x01);
		mplew.write(0x01);
		mplew.writeLong(account.getConnectionAuthentication());
		return mplew.getPacket();		
	}
	
	/**
	 * Gets the packet for a failed login action because the account is unverified.
	 * See packet documentation on CLogin::OnCheckPasswordResult for more information.
	 * @return the packet for login failure for an unverified account
	 */
	public static MaplePacket getAccountUnverified() {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeOpcode(SendOpcode.CheckPassword);
		mplew.write(0x00);
		mplew.write(0x02);
		mplew.writeInt(0x00);
		return mplew.getPacket();
	}
	
	/**
	 * Gets the packet to display a login failed message.
	 * See packet documentation on CLogin::OnCheckPasswordResult for <code>reason</code>.
	 * @param reason the reason the login failed
	 * @return the packet to display a login failed message.
	 */
	public static MaplePacket getLoginFailed(byte reason) {
		MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(8);
		mplew.writeOpcode(SendOpcode.CheckPassword);
		mplew.write(reason);
		mplew.write(0x00);
		mplew.writeInt(0x00);
		return mplew.getPacket();
	}
}
