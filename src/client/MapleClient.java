package client;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import tools.server.Scheduler;
import client.core.CryptoClient;
import client.events.KeepAliveEvent;
import constants.SourceConstants;

/**
 * A connected client, their account, and their current character.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MapleClient implements CryptoClient {
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
	
	/**
	 * Gets whether or not the client is connected.
	 * @return whether or not the client is connected
	 */
	public boolean isConnected() {
		return session.isConnected();
	}
	
	/**
	 * Closes the client's session, disconnecting them.
	 */
	public void disconnect() {
		session.close();
	}
	
	/**
	 * Tells the client that it has recieved a KeepAlive packet.
	 */
	public void keepAliveRecieved() {
		lastKeepAliveRecv = System.currentTimeMillis();
	}
	
	/**
	 * Gets the timestamp of the last KeepAlive packet received in milliseconds.
	 * @return the timestamp of the last KeepAlive packet recieved 
	 */
	public long getLastKeepAliveRecieved() {
		return lastKeepAliveRecv;
	}
	
	/**
	 * Gets the time since the last KeepAlive packet was recieved in milliseconds.
	 * @return the time since the last KeepAlive packet was recieved
	 */
	public long getTimeSinceLastKeepAlive() {
		return (System.currentTimeMillis() - lastKeepAliveRecv);
	}
	
	/**
	 * Schedules a <code>Runnable</code> to send KeepAlive packets to the client.
	 */
	public void scheduleKeepAlive() {
		Scheduler.getInstance().scheduleNow(new KeepAliveEvent(session), SourceConstants.KEEPALIVE_PERIOD);
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
