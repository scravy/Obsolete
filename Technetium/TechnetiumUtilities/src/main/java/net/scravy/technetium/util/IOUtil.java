package net.scravy.technetium.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for easily working with files and other streams.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class IOUtil {

	/**
	 * An instance of the US-ASCII Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset USASCII;

	/**
	 * An instance of the ISO-8859-1 Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset ISO88591;

	/**
	 * An instance of the UTF-8 Charset or null if this Charset is not available
	 * in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF8;

	/**
	 * An instance of the UTF-16 Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF16;

	/**
	 * An instance of the UTF-16 Big Endian Charset or null if this Charset is
	 * not available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF16BE;

	/**
	 * An instance of the UTF-16 Little Endian Charset or null if this Charset
	 * is not available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF16LE;

	/**
	 * An instance of the UTF-32 Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF32;

	/**
	 * An instance of the UTF-32 BE Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF32BE;

	/**
	 * An instance of the UTF-32 LE Charset or null if this Charset is not
	 * available in this instance of the Java Virtual Machine.
	 * 
	 * @since 1.0
	 */
	public static final Charset UTF32LE;

	static {
		Charset charset;

		charset = null;
		try {
			charset = Charset.forName("US-ASCII");
		} catch (UnsupportedCharsetException exc) {

		}
		USASCII = charset;

		charset = null;
		try {
			charset = Charset.forName("ISO-8859-1");
		} catch (UnsupportedCharsetException exc) {

		}
		ISO88591 = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-8");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF8 = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-16");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF16 = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-16BE");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF16BE = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-16LE");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF16LE = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-32");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF32 = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-32BE");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF32BE = charset;

		charset = null;
		try {
			charset = Charset.forName("UTF-32LE");
		} catch (UnsupportedCharsetException exc) {

		}
		UTF32LE = charset;
	}

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	IOUtil() {
	}

	/**
	 * Retrieve data from a file identified by the given file name. If the
	 * fileName starts with “http://”, “https://”, or “ftp://”, a URL is created
	 * and passed to {@link #getBytes(URL)}.
	 * 
	 * @param fileName
	 *            The name of the file.
	 * @return A byte-array containing the data from the file.
	 */
	public static byte[] getBytes(final String fileName) {
		if (fileName.startsWith("http://") || fileName.startsWith("https://")
				|| fileName.startsWith("ftp://")) {
			try {
				return getBytes(new URL(fileName));
			} catch (MalformedURLException exc) {
				throw new RuntimeIOException(exc);
			}
		}
		return getBytes(new File(fileName));
	}

	/**
	 * Retrieve data from a File.
	 * 
	 * @param file
	 *            The file
	 * @throws RuntimeIOException
	 * @return A byte-array containing the data from the file.
	 */
	public static byte[] getBytes(final File file) {
		try {
			InputStream f = new FileInputStream(file);
			int size = (int) file.length();
			byte[] result = new byte[size];
			f.read(result, 0, size);
			return result;
		} catch (FileNotFoundException exc) {
			return null;
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Retrieve data from an URL.
	 * 
	 * @param url
	 *            The URL.
	 * @return A byte-array containing the data located at the given URL.
	 */
	public static byte[] getBytes(final URL url) {
		try {
			URLConnection connection = url.openConnection();
			int length = connection.getContentLength();
			if (length >= 0) {
				byte[] result = new byte[length];
				connection.getInputStream().read(result, 0, length);
				return result;
			}
			InputStream stream = connection.getInputStream();
			return stream.toString().getBytes();
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Completely retrieve the contents of an InputStream as a String.
	 * 
	 * @param inputStream
	 *            The InputStream.
	 * @return The contents of the InputStream.
	 */
	public static String getContents(final InputStream inputStream) {
		try {
			StringBuilder b = new StringBuilder();
			InputStreamReader r = new InputStreamReader(inputStream);
			char[] chars = new char[1024];
			int length = 0;
			while ((length = r.read(chars)) > 0) {
				b.append(chars, 0, length);
			}
			return b.toString();
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Completely retrieve the contents from a given Reader.
	 * 
	 * @param reader
	 *            The Reader.
	 * @return The contents from the Reader.
	 */
	public static String getContents(final Reader reader) {
		try {
			StringBuilder b = new StringBuilder();
			char[] chars = new char[1024];
			int length = 0;
			while ((length = reader.read(chars)) > 0) {
				b.append(chars, 0, length);
			}
			return b.toString();
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Completely retrieve the contents of a file identified by the given file
	 * name.
	 * 
	 * @param file
	 *            The name of the file to be read.
	 * @return The contents of the file or null if the contents could not be
	 *         read due to an IOException (for example if the file does not
	 *         exist).
	 */
	public static String getContents(final String file) {
		return new String(getBytes(new File(file)));
	}

	/**
	 * Completely retrieve the contents of a file.
	 * 
	 * @param fileName
	 *            The file to be read.
	 * @throws RuntimeIOException
	 * @return The contents of the file or null if the contents could not be
	 *         read due to an IOException (for example if the file does not
	 *         exist).
	 */
	public static String getContents(final File fileName) {
		return new String(getBytes(fileName));
	}

	/**
	 * Completely retrieve the contents of a file identified by the given file
	 * name, using the given Charset to decode its contents.
	 * 
	 * @param fileName
	 *            The name of the file to be read.
	 * @param charset
	 *            The charset to be used to decode the contents of the file.
	 * @return The contents of the file or null if the contents could not be
	 *         read due to an IOException (for example if the file does not
	 *         exist).
	 */
	public static String getContents(final String fileName,
			final Charset charset) {
		return new String(getBytes(new File(fileName)), charset);
	}

	/**
	 * Completely retrieve the contents of a file, using the given Charset to
	 * decode its contents.
	 * 
	 * @param file
	 *            The file to be read.
	 * @param charset
	 *            The charset to be used to decode the contents of the file.
	 * @return The contents of the file or null if the contents could not be
	 *         read due to an IOException (for example if the file does not
	 *         exist).
	 */
	public static String getContents(final File file, final Charset charset) {
		return new String(getBytes(file), charset);
	}

	/**
	 * Writes a String to a file.
	 * 
	 * @param file
	 *            The file.
	 * @param contents
	 *            The string.
	 * @param charset
	 *            The charset used to write the file.
	 * @throws RuntimeIOException
	 *             If the file can not be written.
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static void putContents(final File file, final String contents,
			final Charset charset) {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			FileOutputStream stream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
			writer.write(contents);
			writer.close();
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	private static enum CSVParserState {
		START, STRING, QUOTE, FIELD
	}

	/**
	 * 
	 * @param file
	 *            The file to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the file.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static List<List<String>> loadCSV(final File file,
			final Charset charset, final char delimiter)
			throws RuntimeIOException {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			return loadCSV(new FileInputStream(file), charset, delimiter, '"');
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * 
	 * @param file
	 *            The file to load CSV data from.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static List<List<String>> loadCSV(final File file,
			final char delimiter) throws RuntimeIOException {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			return loadCSV(new FileInputStream(file), Charset.forName("UTF-8"),
					delimiter, '"');
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * 
	 * @param file
	 *            The file to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the file.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static List<List<String>> loadCSV(final File file,
			final Charset charset) throws RuntimeIOException {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			return loadCSV(new FileInputStream(file), charset, ',', '"');
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * 
	 * @param file
	 *            The file to load CSV data from.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static List<List<String>> loadCSV(final File file)
			throws RuntimeIOException {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			return loadCSV(new FileInputStream(file), Charset.forName("UTF-8"),
					',', '"');
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * 
	 * @param file
	 *            The file to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the file.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @param quote
	 *            The char used to quote fields (usually a double quotation mark
	 *            (")).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If file is null.
	 */
	public static List<List<String>> loadCSV(final File file,
			final Charset charset, final char delimiter, final char quote)
			throws RuntimeIOException {
		if (file == null) {
			throw new IllegalArgumentException("`file` must not be null.");
		}
		try {
			return loadCSV(new FileInputStream(file), charset, delimiter, quote);
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the InputStream.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final Charset charset, final char delimiter)
			throws RuntimeIOException {
		if (stream == null) {
			throw new IllegalArgumentException("`stream` must not be null.");
		}
		return loadCSV(stream, charset, delimiter, '"');
	}

	/**
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final char delimiter) throws RuntimeIOException {
		if (stream == null) {
			throw new IllegalArgumentException("`stream` must not be null.");
		}
		return loadCSV(stream, Charset.forName("UTF-8"), delimiter, '"');
	}

	/**
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the InputStream.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final Charset charset) throws RuntimeIOException {
		if (stream == null) {
			throw new IllegalArgumentException("`stream` must not be null.");
		}
		return loadCSV(stream, charset, ',', '"');
	}

	/**
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream)
			throws RuntimeIOException {
		if (stream == null) {
			throw new IllegalArgumentException("`stream` must not be null.");
		}
		return loadCSV(stream, Charset.forName("UTF-8"), ',', '"');
	}

	/**
	 * Shorthand for <code>loadCSV(stream, Charset.forName("UTF-8"), ',', '"',
				expectedNumberOfLines, expectedFieldsPerLine, true);</code>
	 * .
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @param expectedNumberOfLines
	 *            The expected number of lines.
	 * @param expectedFieldsPerLine
	 *            The expected number of fields per line.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final int expectedNumberOfLines, final int expectedFieldsPerLine)
			throws RuntimeIOException {
		if (stream == null) {
			throw new IllegalArgumentException("`stream` must not be null.");
		}
		return loadCSV(stream, Charset.forName("UTF-8"), ',', '"',
				expectedNumberOfLines, expectedFieldsPerLine, true);
	}

	/**
	 * Equals
	 * {@link #loadCSV(InputStream, Charset, char, char, int, int, boolean)
	 * loadCSV(stream, charset, delimiter, quote, 1000, 20, true)}.
	 * 
	 * @param stream
	 *            The InputStream to load CSV data from.
	 * @param charset
	 *            The Charset used to decode the InputStream.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @param quote
	 *            The char used to quote fields (usually a double quotation mark
	 *            (")).
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final Charset charset, final char delimiter, final char quote) {
		return loadCSV(stream, charset, delimiter, quote, 1000, 20, true);
	}

	private static String cachedString(final Map<String, String> cache,
			final String string) {
		if (cache == null) {
			return string;
		}
		if (cache.containsKey(string)) {
			return cache.get(string);
		}
		cache.put(string, string);
		return string;
	}

	/**
	 * Load CSV Data from an arbitrary InputStream.
	 * 
	 * @param stream
	 *            The InputStream.
	 * @param charset
	 *            The Charset used to decode the InputStream.
	 * @param delimiter
	 *            The delimiter between fields / columns (usually “,” or “;”).
	 * @param quote
	 *            The char used to quote fields (usually a double quotation mark
	 *            (")).
	 * @param expectedNumberOfLines
	 *            A hint on how many lines are to be expected (to reserve enough
	 *            memory before hand).
	 * @param expectedFieldsPerLine
	 *            A hint on how many fields are to be expected per line.
	 * @param useStringCache
	 *            Whether Strings should be cached. This will create only one
	 *            String object for two identical values. This may increase the
	 *            time used to parse data but reduce memory consumption.
	 * @return A List of List of Strings. Each list represents line, and each
	 *         inner list holds the fields of that line. Every field is
	 *         represented as a String.
	 * @throws RuntimeIOException
	 *             If something went wrong reading data from the InputStream
	 *             (depends on what kind of InputStream and Charset you fed to
	 *             this method).
	 * @throws IllegalArgumentException
	 *             If `stream` or `charset` are null.
	 */
	public static List<List<String>> loadCSV(final InputStream stream,
			final Charset charset, final char delimiter, final char quote,
			final int expectedNumberOfLines, final int expectedFieldsPerLine,
			final boolean useStringCache) {
		if (stream == null) {
			throw new IllegalArgumentException(
					"The InputStream `stream` must not be null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException(
					"The specified `charset` must not be null");
		}
		try {
			Reader in = new BufferedReader(new InputStreamReader(stream,
					charset));
			Map<String, String> cache = null;
			if (useStringCache) {
				cache = new HashMap<String, String>();
			}
			ArrayList<List<String>> lines = new ArrayList<List<String>>(
					expectedNumberOfLines);
			lines.add(new ArrayList<String>(expectedFieldsPerLine));
			StringBuilder builder = new StringBuilder();
			int chr;
			CSVParserState state = CSVParserState.START;
			loop: for (;;) {
				chr = in.read();
				if (chr == '\r') {
					continue;
				}
				switch (state) {
				case START:
					if (chr < 0) {
						lines.get(lines.size() - 1).add("");
						break loop;
					} else if (chr == quote) {
						state = CSVParserState.STRING;
					} else if (chr == delimiter) {
						lines.get(lines.size() - 1).add("");
					} else if (chr == '\n') {
						if (!lines.get(lines.size() - 1).isEmpty()) {
							lines.get(lines.size() - 1).add("");
							lines.add(new ArrayList<String>());
						}
					} else {
						state = CSVParserState.FIELD;
						builder.append((char) chr);
					}
					break;
				case STRING:
					if (chr < 0) {
						break loop;
					} else if (chr == quote) {
						state = CSVParserState.QUOTE;
					} else {
						builder.append((char) chr);
					}
					break;
				case QUOTE:
					if (chr == quote) {
						state = CSVParserState.STRING;
						builder.append(quote);
					} else if (chr == delimiter) {
						state = CSVParserState.START;
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString()));
						builder.setLength(0);
					} else if (chr == '\n') {
						state = CSVParserState.START;
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString()));
						builder.setLength(0);
						lines.add(new ArrayList<String>(expectedFieldsPerLine));
					} else if (chr < 0) {
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString()));
						break loop;
					}
					break;
				case FIELD:
					if (chr == delimiter) {
						state = CSVParserState.START;
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString().trim()));
						builder.setLength(0);
					} else if (chr == '\n') {
						state = CSVParserState.START;
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString().trim()));
						builder.setLength(0);
						lines.add(new ArrayList<String>(expectedFieldsPerLine));
					} else if (chr < 0) {
						lines.get(lines.size() - 1).add(
								cachedString(cache, builder.toString().trim()));
						break loop;
					} else {
						builder.append((char) chr);
					}
					break;
				}
			}
			if (useStringCache) {
				System.gc();
			}
			return lines;
		} catch (IOException exc) {
			throw new RuntimeIOException(exc);
		}
	}

}
