package client;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import server.tools.Scheduler;
import client.core.MapleObject;
import client.events.KeepAlive;
import constants.SourceConstants;

/**
 * A connected client, and their account.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MapleClient extends MapleObject {
	private MapleObfuscator send;
	private MapleObfuscator recv;
	private Channel session;
	private long lastKeepAliveRecv = -1;
	
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
	
	/**
	 * Gets the packet obfuscator for sending packets.
	 * @return the <code>MapleObfuscator</code> handling packets being sent
	 */
	public MapleObfuscator getSendCrypto() {
		return send;
	}
	
	/**
	 * Gets the packet obfuscator for receiving packets.
	 * @return the <code>MapleObfuscator</code> handling packets being received
	 */
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
		Scheduler.getInstance().scheduleNow(new KeepAlive(session), SourceConstants.KEEPALIVE_PERIOD);
	}
}
