package net.scravy.technetium.util;

import static java.util.zip.Deflater.BEST_COMPRESSION;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * Static methods for base64 encoding Strings and Serializable objects.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class Base64 {

	private final static char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '+', '/' };

	private static int[] toInt = new int[128];

	static {
		for (int i = 0; i < ALPHABET.length; i++) {
			toInt[ALPHABET[i]] = i;
		}
	}

	/**
	 * Decode a base64 encoded string.
	 * 
	 * @param string
	 *            The base64 encoded string.
	 * @return A byte-array with the decoded value. Use
	 *         <code>new String(decode(...))</code> to obtain a string.
	 */
	public static byte[] decode(final String string) {
		int delta = string.endsWith("==") ? 2 : string.endsWith("=") ? 1 : 0;
		byte[] buffer = new byte[((string.length() * 3) / 4) - delta];
		int mask = 0xFF;
		int index = 0;
		for (int i = 0; i < string.length(); i += 4) {
			int c0 = toInt[string.charAt(i)];
			int c1 = toInt[string.charAt(i + 1)];
			buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
			if (index >= buffer.length) {
				return buffer;
			}
			int c2 = toInt[string.charAt(i + 2)];
			buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
			if (index >= buffer.length) {
				return buffer;
			}
			int c3 = toInt[string.charAt(i + 3)];
			buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
		}
		return buffer;
	}

	/**
	 * Decode a base64 encoded string into an object.
	 * 
	 * @param string
	 *            The base64 encoded string.
	 * @param type
	 *            The class of the serialized object.
	 * @return The unserialized object or null if the object could not be
	 *         unserialized (for example if the string was corrupted or the
	 *         given class does not match the serialized object).
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T decode(final String string,
			final Class<T> type) {
		try {
			ByteArrayInputStream bytes = new ByteArrayInputStream(
					decode(string));
			InputStream filter = new InflaterInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(filter);
			return (T) in.readObject();
		} catch (Exception exc) {
			return null;
		}
	}

	/**
	 * Encode the given byte array using base64 encoding.
	 * 
	 * @param buffer
	 *            The byte array.
	 * @return A base64 encoded string.
	 */
	public static String encode(final byte[] buffer) {
		int size = buffer.length;
		char[] arr = new char[((size + 2) / 3) * 4];
		int a = 0;
		int i = 0;
		while (i < size) {
			byte one = buffer[i++];
			byte two = (i < size) ? buffer[i++] : 0;
			byte three = (i < size) ? buffer[i++] : 0;

			int mask = 0x3F;
			arr[a++] = ALPHABET[(one >> 2) & mask];
			arr[a++] = ALPHABET[((one << 4) | ((two & 0xFF) >> 4)) & mask];
			arr[a++] = ALPHABET[((two << 2) | ((three & 0xFF) >> 6)) & mask];
			arr[a++] = ALPHABET[two & mask];
		}
		switch (size % 3) {
		case 1:
			arr[--a] = '=';
			//$FALL-THROUGH$
		case 2:
			arr[--a] = '=';
		}
		return new String(arr);
	}

	/**
	 * Encode the given serializable object using base64 encoding.
	 * 
	 * @param object
	 *            The object to be serialized.
	 * @return A base64 encoded string.
	 */
	public static String encode(final Serializable object) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			OutputStream filter = new DeflaterOutputStream(bytes,
					new Deflater(BEST_COMPRESSION));
			ObjectOutputStream out = new ObjectOutputStream(filter);
			out.writeObject(object);
			out.close();
			return encode(bytes.toByteArray());
		} catch (IOException exc) {
			return null;
		}
	}

	/**
	 * Encode the given string using base64 encoding.
	 * 
	 * @param string
	 *            The string to be encoded.
	 * @return The base64 encoded string.
	 */
	public static String encode(final String string) {
		try {
			return encode(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException exc) {
			return encode(string.getBytes());
		}
	}
}