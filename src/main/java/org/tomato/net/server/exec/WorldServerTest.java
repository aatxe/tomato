package org.tomato.net.server.exec;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.net.server.world.WorldServer;

/**
 * A testing application for the world server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldServerTest.class);
    
	/**
	 * Starts the server.
	 * @param args does nothing.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		WorldServer world = new WorldServer(8383);
		LOGGER.info("Server bound on 8383.");
		while (true) {
			if (in.nextLine().equalsIgnoreCase("exit")) {
				world.shutdown();
				LOGGER.info("Shutdown complete.");
				break;
			}
		}
	}
}
