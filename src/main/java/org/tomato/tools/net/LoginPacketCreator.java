package org.tomato.tools.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.opcodes.SendOpcode;
import org.tomato.tools.TimeTool;
import org.tomato.client.MapleAccount;

import static org.jboss.netty.buffer.ChannelBuffers.*;
import static org.tomato.tools.net.Encoders.*;

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
	public static ChannelBuffer getLoginSuccess(MapleAccount account) {
		ChannelBuffer buffer = buffer(LITTLE_ENDIAN, 8);
		buffer.writeShort(SendOpcode.CheckPassword.getOpcode());
		buffer.writeBytes(new byte[] { 0x00, 0x01 });
		buffer.writeZero(4);
		buffer.writeInt(account.getAccountId());
		buffer.writeByte(account.getGender());
		buffer.writeByte((account.isGM()) ? 0x01 : 0x00);
		buffer.writeShort((account.isGM()) ? 0x80 : 0x00);
		buffer.writeZero(1);
		buffer.writeBytes(mapleAsciiString(account.getAccountName()));
		buffer.writeZero(1);
		buffer.writeByte((account.isTradeBlocked()) ? 0x01 : 0x00);
		buffer.writeLong(TimeTool.unixToFiletime(account.getUnixTradeBlockExpiration()));
		buffer.writeZero(1);
		buffer.writeLong(TimeTool.unixToFiletime(account.getUnixCreationDate()));
		buffer.writeInt(0x0C);
		buffer.writeByte(0x01);
		buffer.writeByte(0x01);
		buffer.writeLong(account.getConnectionAuthentication());
		return buffer;	
	}
	
	/**
	 * Gets the packet for a failed login action because the account is unverified.
	 * See packet documentation on CLogin::OnCheckPasswordResult for more information.
	 * @return the packet for login failure for an unverified account
	 */
	public static ChannelBuffer getAccountUnverified() {
		ChannelBuffer buffer = buffer(LITTLE_ENDIAN, 8);
		buffer.writeShort(SendOpcode.CheckPassword.getOpcode());
		buffer.writeByte(0x00);
		buffer.writeByte(0x02);
		buffer.writeInt(0x00);
		return buffer;
	}
	
	/**
	 * Gets the packet to display a login failed message.
	 * See packet documentation on CLogin::OnCheckPasswordResult for <code>reason</code>.
	 * @param reason the reason the login failed
	 * @return the packet to display a login failed message.
	 */
	public static ChannelBuffer getLoginFailed(byte reason) {
		ChannelBuffer buffer = buffer(LITTLE_ENDIAN, 8);
		buffer.writeShort(SendOpcode.CheckPassword.getOpcode());
		buffer.writeByte(reason);
		buffer.writeByte(0x00);
		buffer.writeInt(0x00);
		return buffer;
	}
}
