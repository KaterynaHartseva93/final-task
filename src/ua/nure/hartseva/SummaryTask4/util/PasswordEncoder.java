package ua.nure.hartseva.SummaryTask4.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

	private static final String ALGORYTHM = "MD5";
	private static final String ENCODING = "UTF-8";

	public static String encode(String password) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance(ALGORYTHM);
			// Add password bytes to digest
			md.update(password.getBytes(ENCODING));
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
			return generatedPassword;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No such algorythm.");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Encoding is not supported.");
		}
	}
}
