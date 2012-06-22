package client;

import net.encryption.MapleObfuscator;
import org.jboss.netty.channel.Channel;
import client.core.MapleObject;

public class MapleClient extends MapleObject {
	private MapleObfuscator send;
	private MapleObfuscator recv;
	private Channel session;
	
	public MapleClient(Channel session, MapleObfuscator send, MapleObfuscator recv) {
		this.session = session;
		this.send = send;
		this.recv = recv;
	}
	
	public MapleObfuscator getSendCrypto() {
		return send;
	}
	
	public MapleObfuscator getRecvCrypto() {
		return recv;
	}
	
	public void disconnect() {
		session.close();
	}
}
