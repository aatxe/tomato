package org.tomato.net.server.exec;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.net.server.login.LoginServer;

/**
 * A testing application for the login server.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LoginServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServerTest.class);
    
	/**
	 * Starts the server.
	 * @param args does nothing
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		LoginServer login = new LoginServer(8484);
		LOGGER.info("Server bound on 8484.");
		while (true) {
			if (in.nextLine().equalsIgnoreCase("exit")) {
				login.shutdown();
				LOGGER.info("Shutdown complete.");
				break;
			}
		}
	}
}
