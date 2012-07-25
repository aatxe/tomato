package org.tomato.net.server.handlers.internal;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tomato.tools.net.InternalPacketCreator;
import org.tomato.client.internal.InternalClient;
import org.tomato.net.server.internal.InternalPacketHandler;

public class KeepAliveHandler extends InternalPacketHandler {
	@Override
	public void process(ChannelBuffer buffer, InternalClient c) {
		c.getChannel().write(InternalPacketCreator.getKeepAlive());
	}
}
