/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava;

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



@Author("Julian Fleischer")
@Version("2011-06-07")
public class Base64 {

	private final static char[] ALPHABET = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z',
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'+', '/' };

	private static int[] $toInt = new int[128];

	static {
		for (int i = 0; i < ALPHABET.length; i++) {
			$toInt[ALPHABET[i]] = i;
		}
	}


	public static byte[] decode(final String $string) {
		int $delta = $string.endsWith("==") ? 2 : $string.endsWith("=") ? 1 : 0;
		byte[] $buffer = new byte[(($string.length() * 3) / 4) - $delta];
		int $mask = 0xFF;
		int $index = 0;
		for (int i = 0; i < $string.length(); i += 4) {
			int c0 = $toInt[$string.charAt(i)];
			int c1 = $toInt[$string.charAt(i + 1)];
			$buffer[$index++] = (byte) (((c0 << 2) | (c1 >> 4)) & $mask);
			if ($index >= $buffer.length) {
				return $buffer;
			}
			int c2 = $toInt[$string.charAt(i + 2)];
			$buffer[$index++] = (byte) (((c1 << 4) | (c2 >> 2)) & $mask);
			if ($index >= $buffer.length) {
				return $buffer;
			}
			int c3 = $toInt[$string.charAt(i + 3)];
			$buffer[$index++] = (byte) (((c2 << 6) | c3) & $mask);
		}
		return $buffer;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T decode(final String $string, @SuppressWarnings("unused") final Class<T> $type) {
		try {
			ByteArrayInputStream $bytes = new ByteArrayInputStream(decode($string));
			InputStream $filter = new InflaterInputStream($bytes);
			ObjectInputStream $in = new ObjectInputStream($filter);
			return (T) $in.readObject();
		} catch (Exception $exc) {
			return null;
		}
	}


	public static String encode(final byte[] $buffer) {
		int $size = $buffer.length;
		char[] $arr = new char[(($size + 2) / 3) * 4];
		int $a = 0;
		int $i = 0;
		while ($i < $size) {
			byte $0 = $buffer[$i++];
			byte $1 = ($i < $size) ? $buffer[$i++] : 0;
			byte $2 = ($i < $size) ? $buffer[$i++] : 0;

			int $mask = 0x3F;
			$arr[$a++] = ALPHABET[($0 >> 2) & $mask];
			$arr[$a++] = ALPHABET[(($0 << 4) | (($1 & 0xFF) >> 4)) & $mask];
			$arr[$a++] = ALPHABET[(($1 << 2) | (($2 & 0xFF) >> 6)) & $mask];
			$arr[$a++] = ALPHABET[$2 & $mask];
		}
		switch ($size % 3) {
		case 1:
			$arr[--$a] = '=';
			//$FALL-THROUGH$
		case 2:
			$arr[--$a] = '=';
		}
		return new String($arr);
	}

	public static String encode(final Serializable $object) {
		try {
			ByteArrayOutputStream $bytes = new ByteArrayOutputStream();
			OutputStream $filter = new DeflaterOutputStream($bytes, new Deflater(BEST_COMPRESSION));
			ObjectOutputStream $out = new ObjectOutputStream($filter);
			$out.writeObject($object);
			$out.close();
			return encode($bytes.toByteArray());
		} catch (IOException $exc) {
			return null;
		}
	}

	public static String encode(final String $string) {
		try {
			return encode($string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException $exc) {
			return encode($string.getBytes());
		}
	}
}
