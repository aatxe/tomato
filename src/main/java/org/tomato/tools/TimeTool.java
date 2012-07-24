package org.tomato.tools;

/**
 * A toolkit for handling various Time-related conversions.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class TimeTool {
	private static final long UNIX_TO_FILETIME_CONSTANT = 116444736000000000l; 
	
	/**
	 * Converts from the Unix timestamp format to the Windows FILETIME format.
	 * @param unixTime the Unix timestamp to convert
	 * @return the timestamp in FILETIME format
	 */
	public static long unixToFiletime(long unixTime) {
		return (unixTime * 10000) + UNIX_TO_FILETIME_CONSTANT;
	}
	
	/**
	 * Converts from the Windows FILETIME format to the Unix timestamp format.
	 * @param filetime the FILETIME timestamp to convert
	 * @return the timestamp in Unix timestamp format
	 */
	public static long filetimeToUnix(long filetime) {
		return (filetime - UNIX_TO_FILETIME_CONSTANT) / 10000; 
	}
}
