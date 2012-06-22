package net.server.exec;

import java.util.Scanner;
import tools.ConsoleOutput;

public class Server {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ServerBootstrapper exec = new ServerBootstrapper();
		exec.bind(8484);
		ConsoleOutput.print("Bound on 8484.");
		while (true) {
			if (in.nextLine().equalsIgnoreCase("exit")) {
				exec.shutdown();
				ConsoleOutput.print("Shut down.");
				break;
			}
		}
	}
}
