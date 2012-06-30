package net.server.login;

import net.encryption.MapleObfuscator;
import net.server.core.AbstractMapleServerHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import tools.ConsoleOutput;
import tools.net.LoginPacketCreator;
import client.MapleClient;
import constants.ServerConstants;

/**
 * A server handler for a login server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServerHandler extends AbstractMapleServerHandler {
	private ChannelGroup connections = new DefaultChannelGroup("login-connections");
	
	/**
	 * Creates a new handler for a login server.
	 */
	public LoginServerHandler() {
		this.setPacketProcessor(LoginPacketProcessor.getInstance());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		ConsoleOutput.print("Channel opened with " + e.getChannel().getRemoteAddress() + ".");
		connections.add(e.getChannel());
		byte key[] = {0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
		byte ivRecv[] = {70, 114, 122, 82};
		byte ivSend[] = {82, 48, 120, 115};
		ivRecv[3] = (byte) (Math.random() * 255);
		ivSend[3] = (byte) (Math.random() * 255);
		MapleObfuscator send = new MapleObfuscator(key, ivSend, (short) (0xFFFF - ServerConstants.MAJOR_VERSION));
		MapleObfuscator recv = new MapleObfuscator(key, ivRecv, (short) ServerConstants.MAJOR_VERSION);
		MapleClient client = new MapleClient(e.getChannel(), send, recv);
		e.getChannel().write(LoginPacketCreator.getHandshake(ServerConstants.MAJOR_VERSION, ServerConstants.MINOR_VERSION, ivSend, ivRecv));
		e.getChannel().setAttachment(client);
	}

	@Override
	public ChannelGroup getConnections() {
		return connections;
	}
}
