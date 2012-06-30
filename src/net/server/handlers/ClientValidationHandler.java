package net.server.handlers;

import net.server.core.AbstractMaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;
import constants.ServerConstants;

/**
 *  A handler for packets validating a newly connected client.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientValidationHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, MapleClient c) {
		byte region = slea.readByte();
		short majorVersion = slea.readShort();
		short minorVersion = slea.readShort();
		if (region != ServerConstants.REGION_CODE || majorVersion != ServerConstants.MAJOR_VERSION || minorVersion != ServerConstants.MINOR_VERSION) {
			c.disconnect();
		}
	}
}
