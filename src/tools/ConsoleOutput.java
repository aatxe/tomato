package tools;

import java.util.Calendar;
import java.util.TimeZone;
import constants.SourceConstants;

/**
 * A tool for handling console output.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class ConsoleOutput {
	/**
	 * Prints a message to the console followed by a new line.
	 * @param message the message to be printed
	 */
	public static void print(String message) {
		ConsoleOutput.print(message, true);
	}
	
	/**
	 * Prints a message to the console.
	 * @param message the message to be printed
	 * @param newLine whether or not to end the line with a new line
	 */
	public static void print(String message, boolean newLine) {
		if (newLine) {
			System.out.println("[" + SourceConstants.SOURCE_NAME + "] [" + now() + "] " + message);
		} else {
			System.out.print("[" + SourceConstants.SOURCE_NAME + "] [" + now() + "] " + message + ((newLine) ? "\n" : ""));
		}
	}

	/**
	 * Prints a new line to the console.
	 */
	public static void printNewLine() {
		System.out.print("\n");
	}
	
	/**
	 * Gets a timestamp for the current moment in time.
	 * @return the current timestamp
	 */
	public static String now() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		return ((hours + (TimeZone.getDefault().useDaylightTime() ? 0 : -1)) + ":" + ((minutes < 10) ? "0" + minutes : "" + minutes) + ":" + ((seconds < 10) ? "0" + seconds : "" + seconds));
	}
	
	/**
	 * Joins an array into a string from the specified index.
	 * @param arr the array to join
	 * @param start the index to begin joining the array at
	 * @return the joined string from the array
	 */
	public static String joinStringFrom(String arr[], int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < arr.length; i++) {
			builder.append(arr[i]);
			if (i != arr.length - 1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}
}
