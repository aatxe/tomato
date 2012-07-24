package org.tomato.net.server.encryption;

/**
 * A tool for encryption and decryption of packets.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MapleEncryption {
	/**
	 * Shifts a byte to the left by the specified amount, rolling over any excess.
	 * @param in the byte to be shifted
	 * @param count the amount to shift it by
	 * @return the shifted byte
	 */
	private static byte rollLeft(byte in, int count) {
		int tmp = (int) in & 0xFF;
		tmp = tmp << (count % 8);
		return (byte) ((tmp & 0xFF) | (tmp >> 8));
	}

	/**
	 * Shifts a byte to the right by the specified amount, rolling over any excess.
	 * @param in the byte to be shifted
	 * @param count the amount to shift it by
	 * @return the shifted byte
	 */
	private static byte rollRight(byte in, int count) {
		int tmp = (int) in & 0xFF;
		tmp = (tmp << 8) >>> (count % 8);

		return (byte) ((tmp & 0xFF) | (tmp >>> 8));
	}

	/**
	 * Encrypts an array of bytes.
	 * @param data the array of bytes to be encrypted
	 * @return the encrypted array of bytes
	 */
	public static byte[] encryptData(byte data[]) {
		for (int j = 0; j < 6; j++) {
			byte remember = 0;
			byte dataLength = (byte) (data.length & 0xFF);
			if (j % 2 == 0) {
				for (int i = 0; i < data.length; i++) {
					byte cur = data[i];
					cur = rollLeft(cur, 3);
					cur += dataLength;
					cur ^= remember;
					remember = cur;
					cur = rollRight(cur, (int) dataLength & 0xFF);
					cur = ((byte) ((~cur) & 0xFF));
					cur += 0x48;
					dataLength--;
					data[i] = cur;
				}
			} else {
				for (int i = data.length - 1; i >= 0; i--) {
					byte cur = data[i];
					cur = rollLeft(cur, 4);
					cur += dataLength;
					cur ^= remember;
					remember = cur;
					cur ^= 0x13;
					cur = rollRight(cur, 3);
					dataLength--;
					data[i] = cur;
				}
			}
		}
		return data;
	}

	/**
	 * Decrypts an array of bytes.
	 * @param data the array of bytes to be decrypted
	 * @return the decrypted array of bytes
	 */
	public static byte[] decryptData(byte data[]) {
		for (int j = 1; j <= 6; j++) {
			byte remember = 0;
			byte dataLength = (byte) (data.length & 0xFF);
			byte nextRemember = 0;
			if (j % 2 == 0) {
				for (int i = 0; i < data.length; i++) {
					byte cur = data[i];
					cur -= 0x48;
					cur = ((byte) ((~cur) & 0xFF));
					cur = rollLeft(cur, (int) dataLength & 0xFF);
					nextRemember = cur;
					cur ^= remember;
					remember = nextRemember;
					cur -= dataLength;
					cur = rollRight(cur, 3);
					data[i] = cur;
					dataLength--;
				}
			} else {
				for (int i = data.length - 1; i >= 0; i--) {
					byte cur = data[i];
					cur = rollLeft(cur, 3);
					cur ^= 0x13;
					nextRemember = cur;
					cur ^= remember;
					remember = nextRemember;
					cur -= dataLength;
					cur = rollRight(cur, 4);
					data[i] = cur;
					dataLength--;
				}
			}
		}
		return data;
	}
}
