package net.server.handlers;

import net.server.core.AbstractMaplePacketHandler;
import tools.ConsoleOutput;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;

/**
 * A handler for packets indicating a client has previously encountered an error.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientErrorHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, MapleClient c) {
		ConsoleOutput.print("[Decode] " + slea.readMapleAsciiString());
		// TODO: actually log dis shit.
	}
}
