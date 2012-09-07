package net.abusingjava.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.abusingjava.Author;
import net.abusingjava.Experimental;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Author("Julian Fleischer")
@Version("2012-01-10")
@Since("2012-01-10")
public class AbusingIO {

	AbusingIO() {
	}

	public static String serialize(final Object... $object) {
		StringBuilder $b = new StringBuilder();

		serialize($b, $object);

		return $b.toString();
	}

	public static String serialize(final Object $object) {
		StringBuilder $b = new StringBuilder();

		serialize($b, $object);

		return $b.toString();
	}

	private static String readString(final String $string) {
		StringBuilder $b = new StringBuilder();

		for (int $i = 0; $i < $string.length(); $i++) {
			char $c = $string.charAt($i);
			if ($c == '\\') {
				$c = (char) Integer.parseInt($string.substring($i + 2, $i + 6),
						16);
				$i = $i + 5;
			}
			$b.append($c);
		}

		return $b.toString();
	}

	private static void serialize(final StringBuilder $b, final Object $object) {

		if ($object == null) {
			$b.append("null");
		} else if ($object instanceof Boolean) {
			$b.append((Boolean) $object ? "true" : "false");
		} else if ($object instanceof Byte) {
			$b.append('b');
			$b.append($object.toString());
		} else if ($object instanceof Short) {
			$b.append('s');
			$b.append($object.toString());
		} else if ($object instanceof Integer) {
			$b.append('i');
			$b.append($object.toString());
		} else if ($object instanceof Long) {
			$b.append('l');
			$b.append($object.toString());
		} else if ($object instanceof Character) {
			$b.append('#');
			$b.append(Integer.toHexString(((Character) $object).charValue()));
		} else if ($object instanceof Float) {
			$b.append("f");
			$b.append($object.toString());
		} else if ($object instanceof Double) {
			$b.append("d");
			$b.append($object.toString());
		} else if ($object instanceof String) {
			String $string = (String) $object;
			$b.append('"');
			for (int $i = 0; $i < $string.length(); $i++) {
				int $c = $string.charAt($i);
				if ((($c >= 'a') && ($c <= 'z'))
						|| (($c >= 'A') && ($c <= 'Z'))
						|| (($c >= '0') && ($c <= '9'))) {
					$b.append((char) $c);
				} else {
					$b.append(String.format("\\u%04x", $c));
				}
			}
			$b.append('"');
		} else if ($object instanceof Date) {
			$b.append("D");
			$b.append(Long.toString(((Date) $object).getTime(), 36));
		} else if ($object instanceof List) {
			$b.append("L");
			$b.append(((List<?>) $object).size());
			$b.append(';');
			for (Object $e : (List<?>) $object) {
				serialize($b, $e);
				$b.append(',');
			}
		} else if ($object instanceof Set) {
			$b.append("S");
			$b.append(((Set<?>) $object).size());
			$b.append(';');
			for (Object $e : (Set<?>) $object) {
				serialize($b, $e);
				$b.append(',');
			}
		} else if ($object.getClass().isArray()) {
			int $length = Array.getLength($object);
			$b.append("A");
			$b.append($length);
			$b.append(';');
			$b.append($object.getClass().getComponentType().getCanonicalName());
			$b.append(';');
			for (int $i = 0; $i < $length; $i++) {
				serialize($b, Array.get($object, $i));
				$b.append(',');
			}
		}
	}

	public static Object unserialize(final String $string) {
		Object $result = null;

		switch ($string.charAt(0)) {
		case 'b':
			return (byte) Byte.parseByte($string.substring(1));
		case 's':
			return (short) Short.parseShort($string.substring(1));
		case 'i':
			return Integer.parseInt($string.substring(1));
		case 'l':
			return Long.parseLong($string.substring(1));
		case 'f':
			return Float.parseFloat($string.substring(1));
		case 'd':
			return Double.parseDouble($string.substring(1));
		case '#':
			return (char) Integer.parseInt($string.substring(1), 16);
		case '"':
			return readString($string.substring(1, $string.indexOf('"', 1)));
		case 'D': {
			return new Date(Long.parseLong($string.substring(1), 36));
		}
		case 'L': {
			int $offset = $string.indexOf(';', 1);
			int $length = Integer.parseInt($string.substring(1, $offset));
			return unserializeList($string, $length, $offset + 1);
		}
		case 'S': {
			int $offset = $string.indexOf(';', 1);
			int $length = Integer.parseInt($string.substring(1, $offset));
			return unserializeSet($string, $length, $offset + 1);
		}
		case 'A': {
			int $offset = $string.indexOf(';', 1);
			int $length = Integer.parseInt($string.substring(1, $offset));
			return unserializeArray($string, $length, $offset + 1);
		}
		}

		return $result;
	}

	public static List<Object> unserializeList(final String $string,
			final int $length, final int $offset) {
		List<Object> $list = new ArrayList<Object>($length);

		int $from = $offset;
		int $to;
		for (int $j = 0; $j < $length; $j++) {
			switch ($string.charAt($from)) {
			case 'b': {
				$to = $string.indexOf(',', $from);
				byte $b = Byte.parseByte($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($b);
				break;
			}
			case 's': {
				$to = $string.indexOf(',', $from);
				short $s = Short.parseShort($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($s);
				break;
			}
			case 'i': {
				$to = $string.indexOf(',', $from);
				int $i = Integer.parseInt($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($i);
				break;
			}
			case 'l': {
				$to = $string.indexOf(',', $from);
				long $l = Long.parseLong($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($l);
				break;
			}
			case 'f': {
				$to = $string.indexOf(',', $from);
				float $f = Float.parseFloat($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($f);
				break;
			}
			case 'd': {
				$to = $string.indexOf(',', $from);
				double $d = Double.parseDouble($string
						.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($d);
				break;
			}
			case '#': {
				$to = $string.indexOf(',', $from);
				char $c = (char) Integer.parseInt(
						$string.substring($from + 1, $to), 16);
				$from = $to + 1;
				$list.add($c);
				break;
			}
			case '"': {
				$to = $string.indexOf('"', $from);
				String $s = readString($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return $list;
	}

	public static Set<Object> unserializeSet(final String $string,
			final int $length, final int $offset) {
		Set<Object> $list = new HashSet<Object>($length);

		int $from = $offset;
		int $to;
		for (int $j = 0; $j < $length; $j++) {
			switch ($string.charAt($from)) {
			case 'b': {
				$to = $string.indexOf(',', $from);
				byte $b = Byte.parseByte($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($b);
				break;
			}
			case 's': {
				$to = $string.indexOf(',', $from);
				short $s = Short.parseShort($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($s);
				break;
			}
			case 'i': {
				$to = $string.indexOf(',', $from);
				int $i = Integer.parseInt($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($i);
				break;
			}
			case 'l': {
				$to = $string.indexOf(',', $from);
				long $l = Long.parseLong($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($l);
				break;
			}
			case 'f': {
				$to = $string.indexOf(',', $from);
				float $f = Float.parseFloat($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($f);
				break;
			}
			case 'd': {
				$to = $string.indexOf(',', $from);
				double $d = Double.parseDouble($string
						.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($d);
				break;
			}
			case '#': {
				$to = $string.indexOf(',', $from);
				char $c = (char) Integer.parseInt(
						$string.substring($from + 1, $to), 16);
				$from = $to + 1;
				$list.add($c);
				break;
			}
			case '"': {
				$to = $string.indexOf('"', $from);
				String $s = readString($string.substring($from + 1, $to));
				$from = $to + 1;
				$list.add($s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return $list;
	}

	private static Class<?> forName(final String $name) {
		if ($name.equals("Byte")) {
			return Byte.class;
		} else if ($name.equals("Short")) {
			return Short.class;
		} else if ($name.equals("Integer") || $name.equals("Int")) {
			return Integer.class;
		} else if ($name.equals("Long")) {
			return Long.class;
		} else if ($name.equals("Double")) {
			return Double.class;
		} else if ($name.equals("Float")) {
			return Float.class;
		} else if ($name.equals("Boolean") || $name.equals("Bool")) {
			return Boolean.class;
		} else if ($name.equals("Char") || $name.equals("Character")) {
			return Character.class;
		} else if ($name.equals("int")) {
			return int.class;
		} else if ($name.equals("long")) {
			return long.class;
		} else if ($name.equals("byte")) {
			return byte.class;
		} else if ($name.equals("short")) {
			return short.class;
		} else if ($name.equals("boolean")) {
			return boolean.class;
		} else if ($name.equals("char")) {
			return char.class;
		}
		try {
			return Class.forName($name);
		} catch (Exception $exc) {
			return null;
		}
	}

	public static Object unserializeArray(final String $string,
			final int $length, final int $offset) {
		int $from = $offset;
		int $to = $string.indexOf(';', $offset);

		String $componentClassName = $string.substring($from, $to);
		Object $array = Array
				.newInstance(forName($componentClassName), $length);
		$from = $to + 1;

		for (int $j = 0; $j < $length; $j++) {
			switch ($string.charAt($from)) {
			case 'b': {
				$to = $string.indexOf(',', $from);
				byte $b = Byte.parseByte($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $b);
				break;
			}
			case 's': {
				$to = $string.indexOf(',', $from);
				short $s = Short.parseShort($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $s);
				break;
			}
			case 'i': {
				$to = $string.indexOf(',', $from);
				int $i = Integer.parseInt($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $i);
				break;
			}
			case 'l': {
				$to = $string.indexOf(',', $from);
				long $l = Long.parseLong($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $l);
				break;
			}
			case 'f': {
				$to = $string.indexOf(',', $from);
				float $f = Float.parseFloat($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $f);
				break;
			}
			case 'd': {
				$to = $string.indexOf(',', $from);
				double $d = Double.parseDouble($string
						.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $d);
				break;
			}
			case '#': {
				$to = $string.indexOf(',', $from);
				char $c = (char) Integer.parseInt(
						$string.substring($from + 1, $to), 16);
				$from = $to + 1;
				Array.set($array, $j, $c);
				break;
			}
			case '"': {
				$to = $string.indexOf('"', $from);
				String $s = readString($string.substring($from + 1, $to));
				$from = $to + 1;
				Array.set($array, $j, $s);
				break;
			}
			case 'L':
				break;
			case 'S':
				break;
			case 'A':
				break;
			}
		}

		return $array;
	}

	public static final Charset USASCII = Charset.forName("US-ASCII");

	public static final Charset UTF8 = Charset.forName("UTF-8");
	public static final Charset UTF16 = Charset.forName("UTF-16");
	public static final Charset UTF16BE = Charset.forName("UTF-16BE");
	public static final Charset UTF16LE = Charset.forName("UTF-16LE");
	public static final Charset UTF32 = Charset.forName("UTF-32");
	public static final Charset UTF32BE = Charset.forName("UTF-32BE");
	public static final Charset UTF32LE = Charset.forName("UTF-32LE");

	public static final Charset WINDOWS1250 = Charset.forName("windows-1250");
	public static final Charset WINDOWS1251 = Charset.forName("windows-1251");
	public static final Charset WINDOWS1252 = Charset.forName("windows-1252");
	public static final Charset WINDOWS1253 = Charset.forName("windows-1253");
	public static final Charset WINDOWS1254 = Charset.forName("windows-1254");
	public static final Charset WINDOWS1255 = Charset.forName("windows-1255");
	public static final Charset WINDOWS1256 = Charset.forName("windows-1256");
	public static final Charset WINDOWS1257 = Charset.forName("windows-1257");

	public static final Charset ISO88591 = Charset.forName("ISO-8859-1");
	public static final Charset ISO88592 = Charset.forName("ISO-8859-2");
	public static final Charset ISO88593 = Charset.forName("ISO-8859-3");
	public static final Charset ISO88594 = Charset.forName("ISO-8859-4");
	public static final Charset ISO88595 = Charset.forName("ISO-8859-5");
	public static final Charset ISO88596 = Charset.forName("ISO-8859-6");
	public static final Charset ISO88597 = Charset.forName("ISO-8859-7");
	public static final Charset ISO88598 = Charset.forName("ISO-8859-8");
	public static final Charset ISO88599 = Charset.forName("ISO-8859-9");
	public static final Charset ISO885913 = Charset.forName("ISO-8859-13");
	public static final Charset ISO885915 = Charset.forName("ISO-8859-15");

	public static byte[] getBytes(final String $fileName) {
		return getBytes(new File($fileName));
	}

	public static byte[] getBytes(final File $file) {
		try {
			InputStream $f = new FileInputStream($file);
			int $size = (int) $file.length();
			byte[] $result = new byte[$size];
			$f.read($result, 0, $size);
			return $result;
		} catch (FileNotFoundException $exc) {
			return null;
		} catch (IOException $exc) {
			return null;
		}
	}

	public static String fileGetContents(final String $file) {
		return new String(getBytes(new File($file)));
	}

	public static String fileGetContents(final File $file) {
		return new String(getBytes($file));
	}

	public static String fileGetContents(final String $file,
			final Charset $charset) {
		return new String(getBytes(new File($file)), $charset);
	}

	public static String fileGetContents(final File $file,
			final Charset $charset) {
		return new String(getBytes($file), $charset);
	}

	public static byte[] fileGetBytes(final URL $url) {
		try {
			URLConnection $connection = $url.openConnection();
			int $length = $connection.getContentLength();
			if ($length >= 0) {
				byte[] $result = new byte[$length];
				$connection.getInputStream().read($result, 0, $length);
				return $result;
			}
			InputStream $stream = $connection.getInputStream();
			return $stream.toString().getBytes();
		} catch (IOException $exc) {
			$exc.printStackTrace();
		}
		return null;
	}

	@Experimental
	@Since("2012-01-06")
	public static void filePutContents(final File $file,
			final String $contents, final Charset $charset) throws IOException {
		FileOutputStream $stream = new FileOutputStream($file);
		OutputStreamWriter $writer = new OutputStreamWriter($stream, $charset);
		$writer.write($contents);
		$writer.close();
	}

	private static enum CSVParserState {
		START, STRING, QUOTE, FIELD
	}

	/**
	 * Same as
	 * <code>loadCSV(new FileInputStream($file), $charset, $delimiter, '"')</code>
	 * .
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final File $file,
			final Charset $charset, final char $delimiter) throws IOException {
		return loadCSV(new FileInputStream($file), $charset, $delimiter, '"');
	}

	/**
	 * Same as
	 * <code>loadCSV(new FileInputStream($file), Charset.forName("UTF-8"), $delimiter, '"')</code>
	 * .
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final File $file,
			final char $delimiter) throws IOException {
		return loadCSV(new FileInputStream($file), Charset.forName("UTF-8"),
				$delimiter, '"');
	}

	/**
	 * Same as
	 * <code>loadCSV(new FileInputStream($file), $charset, ',', '"')</code>.
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final File $file,
			final Charset $charset) throws IOException {
		return loadCSV(new FileInputStream($file), $charset, ',', '"');
	}

	/**
	 * Same as
	 * <code>loadCSV(new FileInputStream($file), Charset.forName("UTF-8"), ',', '"')</code>
	 * .
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final File $file)
			throws IOException {
		return loadCSV(new FileInputStream($file), Charset.forName("UTF-8"),
				',', '"');
	}

	/**
	 * Same as
	 * <code>loadCSV(new FileInputStream($file), $charset, $delimiter, $quote)</code>
	 * .
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final File $file,
			final Charset $charset, final char $delimiter, final char $quote)
			throws IOException {
		return loadCSV(new FileInputStream($file), $charset, $delimiter, $quote);
	}

	/**
	 * Same as
	 * <code>loadCSV($stream, Charset.forName("UTF-8"), $delimiter, '"')</code>.
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final InputStream $stream,
			final Charset $charset, final char $delimiter) throws IOException {
		return loadCSV($stream, $charset, $delimiter, '"');
	}

	/**
	 * Same as
	 * <code>loadCSV($stream, Charset.forName("UTF-8"), $delimiter, '"')</code>.
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final InputStream $stream,
			final char $delimiter) throws IOException {
		return loadCSV($stream, Charset.forName("UTF-8"), $delimiter, '"');
	}

	/**
	 * Same as <code>loadCSV($stream, $charset, ',', '"')</code>.
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final InputStream $stream,
			final Charset $charset) throws IOException {
		return loadCSV($stream, $charset, ',', '"');
	}

	/**
	 * Same as <code>loadCSV($stream, Charset.forName("UTF-8"), ',', '"')</code>
	 * .
	 * 
	 * @see #loadCSV(InputStream, Charset, char, char)
	 */
	public static List<List<String>> loadCSV(final InputStream $stream)
			throws IOException {
		return loadCSV($stream, Charset.forName("UTF-8"), ',', '"');
	}

	public static List<List<String>> loadCSV(final InputStream $stream,
			final int $expectedNumberOfLines, final int $expectedFieldsPerLine)
			throws IOException {
		return loadCSV($stream, Charset.forName("UTF-8"), ',', '"', $expectedNumberOfLines, $expectedFieldsPerLine, true);
	}

	public static List<List<String>> loadCSV(final InputStream $stream,
			final Charset $charset, final char $delimiter, final char $quote)
			throws IOException {
		return loadCSV($stream, $charset, $delimiter, $quote, 1000, 20, true);
	}

	private static String cachedString(Map<String,String> $cache, String $string) {
		if ($cache == null) {
			return $string;
		}
		if ($cache.containsKey($string)) {
			return $cache.get($string);
		}
		$cache.put($string, $string);
		return $string;
	}
	
	public static List<List<String>> loadCSV(final InputStream $stream,
			final Charset $charset, final char $delimiter, final char $quote,
			final int $expectedNumberOfLines, final int $expectedFieldsPerLine,
			final boolean $useStringCache)
			throws IOException {
		Reader $in = new BufferedReader(new InputStreamReader($stream, $charset));
		Map<String,String> $cache = null;
		if ($useStringCache) {
			$cache = new HashMap<String,String>();
		}
		ArrayList<List<String>> $lines = new ArrayList<List<String>>(
				$expectedNumberOfLines);
		$lines.add(new ArrayList<String>($expectedFieldsPerLine));
		StringBuilder $builder = new StringBuilder();
		int $char;
		CSVParserState $state = CSVParserState.START;
		loop: for (;;) {
			$char = $in.read();
			if ($char == '\r') {
				continue;
			}
			switch ($state) {
			case START:
				if ($char < 0) {
					$lines.get($lines.size() - 1).add("");
					break loop;
				} else if ($char == $quote) {
					$state = CSVParserState.STRING;
				} else if ($char == $delimiter) {
					$lines.get($lines.size() - 1).add("");
				} else if ($char == '\n') {
					if (!$lines.get($lines.size() - 1).isEmpty()) {
						$lines.get($lines.size() - 1).add("");
						$lines.add(new ArrayList<String>());
					}
				} else {
					$state = CSVParserState.FIELD;
					$builder.append((char) $char);
				}
				break;
			case STRING:
				if ($char < 0) {
					break loop;
				} else if ($char == $quote) {
					$state = CSVParserState.QUOTE;
				} else {
					$builder.append((char) $char);
				}
				break;
			case QUOTE:
				if ($char == $quote) {
					$state = CSVParserState.STRING;
					$builder.append($quote);
				} else if ($char == $delimiter) {
					$state = CSVParserState.START;
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString()));
					$builder.setLength(0);
				} else if ($char == '\n') {
					$state = CSVParserState.START;
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString()));
					$builder.setLength(0);
					$lines.add(new ArrayList<String>($expectedFieldsPerLine));
				} else if ($char < 0) {
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString()));
					break loop;
				}
				break;
			case FIELD:
				if ($char == $delimiter) {
					$state = CSVParserState.START;
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString().trim()));
					$builder.setLength(0);
				} else if ($char == '\n') {
					$state = CSVParserState.START;
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString().trim()));
					$builder.setLength(0);
					$lines.add(new ArrayList<String>($expectedFieldsPerLine));
				} else if ($char < 0) {
					$lines.get($lines.size() - 1).add(
							cachedString($cache, $builder.toString().trim()));
					break loop;
				} else {
					$builder.append((char) $char);
				}
				break;
			}
		}
		if ($useStringCache) {
			System.gc();
		}
		return $lines;
	}

	@SuppressWarnings("serial")
	public static void main(final String... $args) {

		String $serialized = serialize(new int[] { 1, 2, 3 });
		System.out.println(Array.get(unserialize($serialized), 2));

		System.out.println(unserializeList("L3;i3,i3,i4,", 3, 3));

		System.out.println(serialize(new ArrayList<Object>() {
			{
				add(new Object[] { 1, 2, 5, 3 });
				add(90);
				add(' ');
				add("huhuä");
			}
		}));
	}

}
