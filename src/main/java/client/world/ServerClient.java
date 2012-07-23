package client.world;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import tools.server.Scheduler;
import client.core.CryptoClient;
import client.core.KeepAliveClient;
import client.events.ServerKeepAliveEvent;
import constants.SourceConstants;

/**
 * A client representing a server's connection to a world server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class ServerClient implements CryptoClient, KeepAliveClient {
	private MapleObfuscator send;
	private MapleObfuscator recv;
	private Channel session;
	private long lastKeepAliveRecv = -1;

	public ServerClient(Channel session, MapleObfuscator send, MapleObfuscator recv) {
		this.session = session;
		this.send = send;
		this.recv = recv;
	}
	
	@Override
	public MapleObfuscator getSendCrypto() {
		return send;
	}

	@Override
	public MapleObfuscator getRecvCrypto() {
		return recv;
	}

	@Override
	public void keepAliveRecieved() {
		lastKeepAliveRecv = System.currentTimeMillis();
	}
	
	@Override
	public long getLastKeepAliveRecieved() {
		return lastKeepAliveRecv;
	}
	
	@Override
	public long getTimeSinceLastKeepAlive() {
		return (System.currentTimeMillis() - lastKeepAliveRecv);
	}
	
	@Override
	public void scheduleKeepAlive() {
		Scheduler.getInstance().scheduleNow(new ServerKeepAliveEvent(session), SourceConstants.KEEPALIVE_PERIOD);
	}

	@Override
	public Channel getChannel() {
		return this.session;
	}
	
	@Override
	public boolean isConnected() {
		return session.isConnected();
	}
	
	@Override
	public void disconnect() {
		session.close();
	}
}
