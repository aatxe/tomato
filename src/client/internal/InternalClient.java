package client.internal;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import client.core.CryptoClient;

/**
 * A client representing an internal server connection.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class InternalClient implements CryptoClient {
	private MapleObfuscator send;
	private MapleObfuscator recv;
	private Channel session;
	
	/**
	 * Constructs a client with a session, and the respective packet obfuscators.
	 * @param session the <code>Channel<code> handling the client's session.
	 * @param send the <code>MapleObfuscator</code> handling packets being sent
	 * @param recv the <code>MapleObfuscator</code> handling packets being received
	 */
	public InternalClient(Channel session, MapleObfuscator send, MapleObfuscator recv) {
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
}
