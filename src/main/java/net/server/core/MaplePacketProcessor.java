package net.server.core;

/**
 * An interface for some very generic packet processors.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public interface MaplePacketProcessor {	
	/**
	 * Resets the packet processor.
	 */
	public void reset();
	
	/**
	 * Registers all the handlers with the packet processor.
	 */
	public void configure();
}
