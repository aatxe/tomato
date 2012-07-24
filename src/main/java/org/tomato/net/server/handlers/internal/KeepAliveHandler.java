package org.tomato.net.server.handlers.internal;

import org.tomato.tools.data.input.SeekableLittleEndianAccessor;
import org.tomato.tools.net.InternalPacketCreator;
import org.tomato.client.internal.InternalClient;
import org.tomato.net.server.internal.InternalPacketHandler;

public class KeepAliveHandler extends InternalPacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, InternalClient c) {
		c.getChannel().write(InternalPacketCreator.getKeepAlive());
	}
}
