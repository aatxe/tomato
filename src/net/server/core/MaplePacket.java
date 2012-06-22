package net.server.core;

public interface MaplePacket {
	public byte[] getBytes();
	public Runnable getOnSend();
	public void setOnSend(Runnable onSend);
}
