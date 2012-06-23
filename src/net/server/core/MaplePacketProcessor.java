package net.server.core;

import net.server.opcodes.RecvOpcode;

public class MaplePacketProcessor {
	private static MaplePacketProcessor instance;
	private MaplePacketHandler[] handlers;
	
	private MaplePacketProcessor() {
		int maxOpcode = 0;
		for (RecvOpcode opcode : RecvOpcode.values()) {
			if (opcode.getOpcode() > maxOpcode) {
				maxOpcode = opcode.getOpcode();
			}
		}
		handlers = new MaplePacketHandler[maxOpcode + 1];
	}
	
	public MaplePacketHandler getHandler(int opcode) {
		return handlers[opcode];
	}
	
	public void register(int opcode, MaplePacketHandler handler) {
		handlers[opcode] = handler;
	}
	
	public void reset() {
		int maxOpcode = 0;
		for (RecvOpcode opcode : RecvOpcode.values()) {
			if (opcode.getOpcode() > maxOpcode) {
				maxOpcode = opcode.getOpcode();
			}
		}
		handlers = new MaplePacketHandler[maxOpcode + 1];
	}
	
	public void configure() {
		// TODO: register some handlers, yo.
	}
	
	public static MaplePacketProcessor getInstance() {
		if (instance == null) {
			instance = new MaplePacketProcessor();
			instance.configure();
		}
		return instance;
	}
}
