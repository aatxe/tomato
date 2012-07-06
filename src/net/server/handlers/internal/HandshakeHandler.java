package net.server.handlers.internal;

import net.encryption.MapleObfuscator;
import net.server.internal.InternalPacketHandler;
import tools.ConsoleOutput;
import tools.data.input.SeekableLittleEndianAccessor;
import client.internal.InternalClient;
import constants.ServerConstants;

public class HandshakeHandler extends InternalPacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, InternalClient c) {
		short majorVersion = slea.readShort();
		short minorVersion = slea.readShort();
		if (majorVersion != ServerConstants.MAJOR_VERSION || minorVersion != ServerConstants.MINOR_VERSION) {
			ConsoleOutput.print("[Internal] Server versions do not match.");
			return;
		}
		slea.readByte(); // throw it away. it's useless.
		byte[] sendIv = new byte[4];
		for (int i = 0; i < sendIv.length; i++) {
			sendIv[i] = slea.readByte();
		}
		byte[] recvIv = new byte[4];
		for (int i = 0; i < sendIv.length; i++) {
			recvIv[i] = slea.readByte();
		}
		byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
		MapleObfuscator send = new MapleObfuscator(key, sendIv, (short) (0xFFFF - majorVersion));
		MapleObfuscator recv = new MapleObfuscator(key, recvIv, majorVersion);
		c.setObfuscators(send, recv);
		ConsoleOutput.print("[Internal] Shook hands.");
	}

}
