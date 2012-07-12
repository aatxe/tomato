package net.server.handlers.internal;

import tools.data.input.SeekableLittleEndianAccessor;
import tools.net.InternalPacketCreator;
import client.internal.InternalClient;
import net.server.internal.InternalPacketHandler;

public class KeepAliveHandler extends InternalPacketHandler {
	@Override
	public void process(SeekableLittleEndianAccessor slea, InternalClient c) {
		c.getChannel().write(InternalPacketCreator.getKeepAlive());
	}
}
