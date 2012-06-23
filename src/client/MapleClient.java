package client;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import client.core.MapleObject;

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
	 * Closes the client's session, disconnecting them.
	 */
	public void disconnect() {
		session.close();
	}
}
