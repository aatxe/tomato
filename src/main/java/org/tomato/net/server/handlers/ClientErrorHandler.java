package org.tomato.net.server.handlers;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.net.server.core.AbstractMaplePacketHandler;
import org.tomato.client.MapleClient;

/**
 * A handler for packets indicating a org.tomato.client has previously encountered an error.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ClientErrorHandler extends AbstractMaplePacketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientErrorHandler.class);

	@Override
	public void process(ChannelBuffer buffer, MapleClient c) {
	    // TODO refactor
        LOGGER.error(buffer.readBytes(buffer.readUnsignedShort()).toString(Charset.forName("ascii")));
	}
}
