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
	 * Constructs a client with only a session.
	 * @param session the <code>Channel<code> handling the client's session.
	 */
	public InternalClient(Channel session) {
		this.session = session;
	}
	
	/**
	 * Constructs a client with a session, and the respective packet obfuscators.
	 * @param session the <code>Channel<code> handling the client's session.
	 * @param send the <code>MapleObfuscator</code> for packets being sent
	 * @param recv the <code>MapleObfuscator</code> for packets being received
	 */
	public InternalClient(Channel session, MapleObfuscator send, MapleObfuscator recv) {
		this.session = session;
		this.send = send;
		this.recv = recv;
	}
	
	/**
	 * Sets the obfuscator for this client.
	 * @param send the new <code>MapleObfuscator</code> for packets being sent
	 * @param recv the new <code>MapleObfuscator</code> for packets being received
	 */
	public void setObfuscators(MapleObfuscator send, MapleObfuscator recv) {
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
