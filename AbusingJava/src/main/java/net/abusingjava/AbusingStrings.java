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

import java.lang.reflect.Constructor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.NodeList;



@Author("Julian Fleischer")
@Since("2011-07-18")
@Version("2011-10-21")
@Stable
public class AbusingStrings {

	public static String repeat(final String $string, final int $num) {
		if ($num < 0)
			throw new IllegalArgumentException("$num may not be negative.");
		StringBuilder $builder = new StringBuilder();
		for (int $i = 0; $i < $num; $i++) {
			$builder.append($string);
		}
		return $builder.toString();
	}
	
	public static String[] explode(final String $string, final String $delimiter) {
		LinkedList<String> $strings = new LinkedList<String>();
		
		int $j = 0;
		int $i = 0;
		while ($i <= ($string.length() - $delimiter.length())) {
			if ($string.substring($i, $i + $delimiter.length()).equals($delimiter)) {
				$strings.add($string.substring($j, $i));
				$i = $j = $i + $delimiter.length();
			} else {
				$i++;
			}
		}
		$strings.add($string.substring($j, $string.length()));
		
		return $strings.toArray(new String[$strings.size()]);
	}
	
	public static String md5(final String $string) {
		MessageDigest $algorithm;
		try {
			$algorithm = java.security.MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException $exc) {
			return null;
		}
		$algorithm.reset();
		$algorithm.update($string.getBytes());
		byte $messageDigest[] = $algorithm.digest();

		StringBuilder $hexString = new StringBuilder();
		for (int $i = 0; $i < $messageDigest.length; $i++) {
			String $hex = Integer.toHexString(0xFF & $messageDigest[$i]);
			if ($hex.length() < 2) $hexString.append('0');
			$hexString.append($hex);
		}

		return $hexString.toString();
	}
	
	public static String capitalize(final String $s) {
		if (($s == null) || $s.isEmpty())
			return $s;
		return $s.substring(0, 1).toUpperCase() + $s.substring(1);
	}

	public static String implode(final Object delimiter, final Iterable<?> strings) {
		return implode(delimiter.toString(), strings, new StringBuilder()).toString();
	}

	public static String implode(final Object entryDelimiter, final Object keyValueDelimiter,
								 final Map<?,?> map) {
		return implode(entryDelimiter.toString(), keyValueDelimiter.toString(), map, new StringBuilder())
				.toString();
	}

	public static String implode(final Object delimiter, final Object[] strings) {
		return implode(delimiter.toString(), strings, new StringBuilder()).toString();
	}

	public static String implode(final Object delimiter, final String[] strings) {
		return implode(delimiter.toString(), strings, new StringBuilder()).toString();
	}

	public static String implode(final String delimiter, final double[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		if (array.length > 0) {
			stringBuilder.append(array[0]);
			for (int i = 1; i < array.length; i++) {
				stringBuilder.append(delimiter);
				stringBuilder.append(array[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static String implode(final String delimiter, final float[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		if (array.length > 0) {
			stringBuilder.append(array[0]);
			for (int i = 1; i < array.length; i++) {
				stringBuilder.append(delimiter);
				stringBuilder.append(array[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static String implode(final String delimiter, final int[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		if (array.length > 0) {
			stringBuilder.append(array[0]);
			for (int i = 1; i < array.length; i++) {
				stringBuilder.append(delimiter);
				stringBuilder.append(array[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static String implode(final String delimiter, final Iterable<?> strings) {
		return implode(delimiter, strings, new StringBuilder()).toString();
	}

	public static StringBuilder implode(final String delimiter, final Iterable<?> strings,
										final StringBuilder result) {
		Iterator<?> it = strings.iterator();
		if (!it.hasNext()) {
			return result;
		}
		while (it.hasNext()) {
			result.append(it.next().toString());
			result.append(delimiter);
		}
		result.setLength(result.length() - delimiter.length());
		return result;
	}

	public static String implode(final String delimiter, final long[] array) {
		StringBuilder stringBuilder = new StringBuilder();
		if (array.length > 0) {
			stringBuilder.append(array[0]);
			for (int i = 1; i < array.length; i++) {
				stringBuilder.append(delimiter);
				stringBuilder.append(array[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static String implode(final String delimiter, final NodeList nodeList) {
		return implode(delimiter, nodeList, new StringBuilder()).toString();
	}

	public static StringBuilder implode(final String delimiter, final NodeList nodeList,
										final StringBuilder stringBuilder) {
		int len = nodeList.getLength();
		if (len > 0) {
			stringBuilder.append(nodeList.item(0).getNodeValue());
			for (int i = 1; i < len; i++) {
				stringBuilder.append(delimiter);
				stringBuilder.append(nodeList.item(0).getNodeValue());
			}
		}
		return stringBuilder;
	}

	public static String implode(final String delimiter, final Object[] strings) {
		return implode(delimiter, strings, new StringBuilder()).toString();
	}

	public static StringBuilder implode(final String $delimiter, final Object[] $strings,
										final StringBuilder $result) {
		if ($strings.length > 0) {
			$result.append(($strings[0] == null) ? "null" : $strings[0].toString());
			for (int i = 1; i < $strings.length; i++) {
				$result.append($delimiter);
				$result.append(($strings[i] == null) ? "null" : $strings[i].toString());
			}
		}
		return $result;
	}

	/**
	 * Implodes the given Map by imploding each entry first and imploding the resulting list.
	 * 
	 * @param $entryDelimiter The delimiter between entries (newline, for example)
	 * @param $keyValueDelimiter The delimiter between key and value (colon, for example)
	 * @param $map The map to pretty print.
	 * @return The imploded String.
	 */
	public static String implode(final String $entryDelimiter, final String $keyValueDelimiter,
								 final Map<?,?> $map) {
		return implode($entryDelimiter, $keyValueDelimiter, $map, new StringBuilder()).toString();
	}

	/**
	 * Implodes the given Map by imploding each entry first and imploding the resulting list.
	 * 
	 * @param $entryDelimiter The delimiter between entries (newline, for example)
	 * @param $keyValueDelimiter The delimiter between key and value (colon, for example)
	 * @param $map The map to pretty print.
	 * @param $result A StringBuilder to append the result on
	 * @return The StringBuilder passed in $result.
	 * @throws IllegalArgumentException If any argument is null.
	 */
	public static StringBuilder implode(final String $entryDelimiter, final String $keyValueDelimiter,
										final Map<?,?> $map, final StringBuilder $result) {
		if (($entryDelimiter == null) || ($keyValueDelimiter == null) || ($map == null) || ($result == null)) {
			throw new IllegalArgumentException("No Argument to implode may be null.");
		}
		if ($map.size() == 0) {
			return $result;
		}
		for (Object $k : $map.keySet()) {
			Object $v = $map.get($k);
			$result.append($k == null ? "null" : $k.toString());
			$result.append($keyValueDelimiter);
			$result.append($v == null ? "null" : $v.toString());
			$result.append($entryDelimiter);
		}
		$result.setLength($result.length() - $entryDelimiter.length());
		return $result;
	}
	
	public static String implode(final String $delimiter, final String[] strings) {
		return implode($delimiter, strings, new StringBuilder()).toString();
	}

	public static StringBuilder implode(final String $delimiter, final String[] $strings,
										final StringBuilder $result) {
		if (($strings == null) || ($delimiter == null) || ($result == null)) {
			throw new IllegalArgumentException();
		}
		if ($strings.length > 0) {
			$result.append($strings[0]);
			for (int i = 1; i < $strings.length; i++) {
				$result.append($delimiter);
				$result.append($strings[i]);
			}
		}
		return $result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(final String $value, final Class<T> $target, final T $default) {
		return (T) convertString($value, $target, $default);
	}
	
	public static Object convertString(final String $value, final Class<?> $target, final Object $default) {
		try {
			if ($target == String.class) {
				return $value;
			} else if (($target == int.class) || ($target == Integer.class)) {
				return Integer.parseInt($value);
			} else if (($target == long.class) || ($target == Long.class)) {
				return Long.parseLong($value);
			} else if (($target == float.class) || ($target == Float.class)) {
				return Float.parseFloat($value);
			} else if (($target == double.class) || ($target == Double.class)) {
				return Double.parseDouble($value);
			} else if (($target == boolean.class) || ($target == Boolean.class)) {
				return Boolean.parseBoolean($value);
			} else if ($target == Date.class) {
				Date $date;
				try {
					$date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ROOT).parse($value);
					if ($date != null) {
						return $date;
					}
				} catch (ParseException $exc) {}
				try {
					$date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse($value);
					if ($date != null) {
						return $date;
					}
				} catch (ParseException $exc) {}
				try {
					$date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse($value);
					if ($date != null) {
						return $date;
					}
				} catch (ParseException $exc) {}
				try {
					$date = new SimpleDateFormat("yyyy-MM-dd").parse($value);
					if ($date != null) {
						return $date;
					}
				} catch (ParseException $exc) {}
			} else {
				Constructor<?> $c = $target.getConstructor(String.class);
				return $c.newInstance($value);
			}
		} catch (Exception $exc) {
		}
		return $default;
	}
	
	public static String wrap(final String $str, final int $wrapLength) {
		if ($str == null) {
			return "";
		}
		
	    int $offset = 0;
	    StringBuilder $resultBuilder = new StringBuilder();

	    while (($str.length() - $offset) > $wrapLength) {
	        if ($str.charAt($offset) == ' ') {
	            $offset++;
	            continue;
	        }

	        int $spaceToWrapAt = $str.lastIndexOf(' ', $wrapLength + $offset);
	        // if the next string with length maxLength doesn't contain ' '
	        if ($spaceToWrapAt < $offset) {
	            $spaceToWrapAt = $str.indexOf(' ', $wrapLength + $offset);
	            // if no more ' '
	            if ($spaceToWrapAt < 0) {
	                break;
	            }
	        }

	        $resultBuilder.append($str.substring($offset, $spaceToWrapAt));
	        $resultBuilder.append("\n");
	        $offset = $spaceToWrapAt + 1;
	    }

	    $resultBuilder.append($str.substring($offset));
	    return $resultBuilder.toString();
	}
	
	public static String takeWhileIsChar(final String $chars, final String $string) {
		StringBuilder $builder = new StringBuilder();
		for (int $i = 0; $i < $string.length(); $i++) {
			if ($chars.indexOf($string.charAt($i)) >= 0) {
				$builder.append($string.charAt($i));
			} else {
				break;
			}
		}
		return $builder.toString();
	}

	public static String takeWhileIsNotChar(final String $chars, final String $string) {
		StringBuilder $builder = new StringBuilder();
		for (int $i = 0; $i < $string.length(); $i++) {
			if ($chars.indexOf($string.charAt($i)) < 0) {
				$builder.append($string.charAt($i));
			} else {
				break;
			}
		}
		return $builder.toString();
	}
	
	public static boolean containsOneOfChars(final String $chars, final String $string) {
		for (int $i = 0; $i < $string.length(); $i++) {
			if ($chars.indexOf($string.charAt($i)) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <code>isEmpty($string)</code> is equivalent to <code>($string == null) || $string.isEmpty()</code>.
	 */
	public static boolean isEmpty(final String $string) {
		return ($string == null) || $string.trim().isEmpty();
	}
	
	/**
	 * Pads $padding to the right of $string until it has the desired $length.
	 */
	public static String strPadRight(final String $string, final int $length, final char $padding) {
		StringBuilder $b = new StringBuilder($string);
		while ($b.length() < $length) {
			$b.append($padding);
		}
		return $b.toString();
	}
	
	/**
	 * Pads $padding to the left of $string until it has the desired $length.
	 */
	public static String strPadLeft(final String $string, int $length, final char $padding) {
		StringBuilder $b = new StringBuilder();
		$length -= $string.length();
		while ($b.length() < $length) {
			$b.append($padding);
		}
		$b.append($string);
		return $b.toString();
	}
	
	/**
	 * Counts the number of occurences of $char in $string.
	 */
	public static int countChar(final String $string, final char $char) {
		int $c = 0;
		for (int $i = 0; $i < $string.length(); $i++) {
			if ($string.charAt($i) == $char) $c++;
		}
		return $c;
	}
}
