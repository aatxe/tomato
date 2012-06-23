package net.server.core;

import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;

public interface MaplePacketHandler {
	public void process(SeekableLittleEndianAccessor slea, MapleClient c);
	public boolean validate(MapleClient c);
}
