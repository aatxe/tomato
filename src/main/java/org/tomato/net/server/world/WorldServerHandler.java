package org.tomato.net.server.world;

import org.tomato.net.encryption.MapleObfuscator;
import org.tomato.net.server.core.AbstractMapleServerHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.tomato.tools.ConsoleOutput;
import org.tomato.tools.net.GlobalPacketCreator;
import org.tomato.client.world.ServerClient;
import org.tomato.constants.ServerConstants;

/**
 * A server handler for a world server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldServerHandler extends AbstractMapleServerHandler {
	private ChannelGroup connections = new DefaultChannelGroup("world-connections");
	
	/**
	 * Creates a new handler for a world server.
	 */
	public WorldServerHandler() {
		this.setPacketProcessor(WorldPacketProcessor.getInstance());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		ConsoleOutput.print("[World] Channel opened with " + e.getChannel().getRemoteAddress() + ".");
		connections.add(e.getChannel());
		byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
		byte ivRecv[] = {0x1A, (byte) 0xFF, 0x14, 0x2E};
		byte ivSend[] = {(byte) 0xC3, 0x05, 0x79, 0x42};
		ivRecv[3] = (byte) (Math.random() * 255);
		ivSend[3] = (byte) (Math.random() * 255);
		MapleObfuscator send = new MapleObfuscator(key, ivSend, (short) (0xFFFF - ServerConstants.MAJOR_VERSION));
		MapleObfuscator recv = new MapleObfuscator(key, ivRecv, (short) ServerConstants.MAJOR_VERSION);
		ServerClient client = new ServerClient(e.getChannel(), send, recv);
		e.getChannel().write(GlobalPacketCreator.getHandshake(ServerConstants.MAJOR_VERSION, ServerConstants.MINOR_VERSION, ivSend, ivRecv));
		e.getChannel().setAttachment(client);
	}

	@Override
	public ChannelGroup getConnections() {
		return connections;
	}
}
