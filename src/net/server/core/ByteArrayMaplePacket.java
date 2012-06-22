package net.server.core;

public class ByteArrayMaplePacket implements MaplePacket {
	Runnable onSend = null;
	private byte[] data;
	
	public ByteArrayMaplePacket(byte[] data) {
		this(data, null);
	}
	
	public ByteArrayMaplePacket(byte[] data, Runnable onSend) {
		this.data = data;
		this.onSend = onSend;
	}
	
	@Override
	public byte[] getBytes() {
		return data;
	}

	@Override
	public Runnable getOnSend() {
		return onSend;
	}

	@Override
	public void setOnSend(Runnable onSend) {
		this.onSend = onSend;
	}
}
