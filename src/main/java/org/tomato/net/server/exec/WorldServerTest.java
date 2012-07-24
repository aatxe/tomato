package org.tomato.net.server.exec;

import java.util.Scanner;
import org.tomato.net.server.world.WorldServer;
import org.tomato.tools.ConsoleOutput;

/**
 * A testing application for the world server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class WorldServerTest {
	/**
	 * Starts the server.
	 * @param args does nothing.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		WorldServer world = new WorldServer(8383);
		ConsoleOutput.print("[World] Server bound on 8383.");
		while (true) {
			if (in.nextLine().equalsIgnoreCase("exit")) {
				world.shutdown();
				ConsoleOutput.print("[World] Shutdown complete.");
				break;
			}
		}
	}
}
