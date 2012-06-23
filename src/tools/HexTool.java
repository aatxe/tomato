package tools;

import java.io.ByteArrayOutputStream;

/**
 * A tool for handling hex data.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class HexTool {
	private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * Converts a byte into a <code>String</code>.
	 * @param byteValue the byte to be converted
	 * @return the byte as a char in <code>String</code> form.
	 */
	private static String toString(byte byteValue) {
		int tmp = byteValue << 8;
		char[] retstr = new char[] {HEX[(tmp >> 12) & 0x0F], HEX[(tmp >> 8) & 0x0F]};
		return String.valueOf(retstr);
	}

	/**
	 * Converts an array of bytes into a <code>String</code>.
	 * @param bytes the bytes to be converted
	 * @return the bytes as a <code>String</code>
	 */
	public static String toString(byte[] bytes) {
		StringBuilder hexed = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			hexed.append(toString(bytes[i]));
			hexed.append(' ');
		}
		return hexed.substring(0, hexed.length() - 1);
	}

	/**
	 * Converts an array of bytes into a hash-like <code>String</code>.
	 * @param bytes the bytes to be converted
	 * @return the bytes as a hash-like <code>String</code>
	 */
	public static String toHashString(byte[] bytes) {
		return HexTool.toString(bytes).replace(" ", "").toLowerCase();
	}
	
	/**
	 * Converts a hex <code>String</code> into a byte array.
	 * @param hex the <code>String</code> to be converted
	 * @return the <code>String</code> as a byte array.
	 */
	public static byte[] getByteArrayFromHexString(String hex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nexti = 0;
		int nextb = 0;
		boolean highoc = true;
		outer: for (;;) {
			int number = -1;
			while (number == -1) {
				if (nexti == hex.length()) {
					break outer;
				}
				char chr = hex.charAt(nexti);
				if (chr >= '0' && chr <= '9') {
					number = chr - '0';
				} else if (chr >= 'a' && chr <= 'f') {
					number = chr - 'a' + 10;
				} else if (chr >= 'A' && chr <= 'F') {
					number = chr - 'A' + 10;
				} else {
					number = -1;
				}
				nexti++;
			}
			if (highoc) {
				nextb = number << 4;
				highoc = false;
			} else {
				nextb |= number;
				highoc = true;
				baos.write(nextb);
			}
		}
		return baos.toByteArray();
	}
}
