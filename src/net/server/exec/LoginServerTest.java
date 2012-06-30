package net.server.exec;

import java.util.Scanner;
import net.server.login.LoginServer;
import tools.ConsoleOutput;

/**
 * A testing application for the login server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServerTest {
	/**
	 * Starts the server.
	 * @param args does nothing
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		LoginServer login = new LoginServer(8484);
		ConsoleOutput.print("[Login] Server bound on 8484.");
		while (true) {
			if (in.nextLine().equalsIgnoreCase("exit")) {
				login.shutdown();
				ConsoleOutput.print("[Login] Shutdown complete.");
				break;
			}
		}
	}
}
