package org.tomato.net.server.handlers;

import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.tools.ConsoleOutput;
import org.tomato.tools.data.input.SeekableLittleEndianAccessor;
import org.tomato.client.MapleClient;

/**
 * A handler for packets indicating a org.tomato.client has previously encountered an error.
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
