package org.tomato.net.server.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.client.MapleClient;
import org.tomato.constants.ServerConstants;

/**
 *  A handler for packets validating a newly connected org.tomato.client.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientValidationHandler extends AbstractMaplePacketHandler {
	@Override
	public void process(ChannelBuffer buffer, MapleClient c) {
		byte region = buffer.readByte();
		short majorVersion = buffer.readShort();
		short minorVersion = buffer.readShort();
		if (region != ServerConstants.REGION_CODE || majorVersion != ServerConstants.MAJOR_VERSION || minorVersion != ServerConstants.MINOR_VERSION) {
			c.disconnect();
		}
	}
}
