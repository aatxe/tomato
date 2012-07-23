package client;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import tools.server.Scheduler;
import client.core.CryptoClient;
import client.core.KeepAliveClient;
import client.events.KeepAliveEvent;
import constants.SourceConstants;

/**
 * A connected client, their account, and their current character.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MapleClient implements CryptoClient, KeepAliveClient {
	private MapleObfuscator send;
	private MapleObfuscator recv;
	private Channel session;
	private long lastKeepAliveRecv = -1;
	private MapleAccount account;
	private MapleCharacter player;
	
	/**
	 * Constructs a client with a session, and the respective packet obfuscators.
	 * @param session the <code>Channel<code> handling the client's session.
	 * @param send the <code>MapleObfuscator</code> handling packets being sent
	 * @param recv the <code>MapleObfuscator</code> handling packets being received
	 */
	public MapleClient(Channel session, MapleObfuscator send, MapleObfuscator recv) {
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
		Scheduler.getInstance().scheduleNow(new KeepAliveEvent(session), SourceConstants.KEEPALIVE_PERIOD);
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
	
	/**
	 * Gets the account that this client is currently logged in to.
	 * @return the current account of this client
	 */
	public MapleAccount getAccount() {
		return this.account;
	}
	
	/**
	 * Gets the character that this client is currently playing.
	 * @return the current character of this client
	 */
	public MapleCharacter getPlayer() {
		return this.player;
	}
}
