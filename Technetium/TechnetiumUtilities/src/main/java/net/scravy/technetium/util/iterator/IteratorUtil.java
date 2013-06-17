package net.scravy.technetium.util.iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import net.scravy.technetium.util.RuntimeIOException;

/**
 * Utilities for working with the vast amount if iterable or not-so-iterable
 * datatypes in java, such as: Enumerations, Arrays, Iterables.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class IteratorUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	IteratorUtil() {
	}

	/**
	 * Creates an infinite iterator, which repeats (cycles) the list again and
	 * again.
	 * <p>
	 * The following will repeat 3, 4, 9, 3, 4, 9, 3, 4, 9, ... again and again.
	 * 
	 * <pre>
	 * List&lt;Integer&gt; list = new ArrayList&lt;Integer&gt;();
	 * list.add(3);
	 * list.add(4);
	 * list.add(9);
	 * for (Integer x : IteratorUtil.cycle(list)) {
	 * 	System.out.println(x);
	 * }
	 * </pre>
	 * 
	 * @param list
	 *            The list to cycle.
	 * @return An iterable object that cycles though the values in the list.
	 * @since 1.0
	 */
	public static <V> Iterable<V> cycle(List<V> list) {
		return new CyclingIterator<V>(list);
	}

	/**
	 * Iterate over an enumeration.
	 * <p>
	 * This function exists to cope with legacy APIs in the first place (like,
	 * for example, the Java Servlet API).
	 * <p>
	 * This way, an enumeration can be used with modern
	 * <code>for (x : in)</code> syntax.
	 * 
	 * @param enumeration
	 *            An enumeration that should be turned into an Iterable.
	 * @return An iterable object that iterates through the values of the
	 *         enumeration.
	 * @since 1.0
	 */
	public static <V> Iterable<V> iterate(Enumeration<V> enumeration) {
		return new EnumerationIterator<V>(enumeration);
	}

	/**
	 * Iterate over an enumeration as if it was Iterable.
	 * 
	 * @param enumeration
	 *            The enumeration.
	 * @param elementType
	 *            The class of the elements in this enumeration.
	 * @return An Iterable object that allows iterating over the enumeration
	 *         once.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <V> Iterable<V> iterate(Enumeration<?> enumeration,
			Class<V> elementType) {
		return new EnumerationIterator<V>((Enumeration<V>) enumeration);
	}

	/**
	 * Iterate over an array as if it was Iterable – useful for methods which
	 * only take an Iterable, when all you've got is an array.
	 * 
	 * @param array
	 *            The array to iterate over.
	 * @return An Iterable object that allows iterating over the array once.
	 * @since 1.0
	 */
	public static <V> Iterable<V> iterate(V... array) {
		return new ArrayIterator<V>(array);
	}

	/**
	 * Create an Iterable which iterates over all permutations of a given list.
	 * 
	 * @param list
	 *            The list.
	 * @return An Iterable which allows iterating over all permutations of the
	 *         list.
	 * @since 1.0
	 */
	public static <V> Iterable<List<V>> permutations(List<V> list) {
		return new PermutationGenerator<V>(list);
	}

	/**
	 * Create an Enumeration form an array – useful for dealing with legacy
	 * APIs.
	 * 
	 * @param array
	 *            The array.
	 * @return An enumeration which represents the array.
	 * @since 1.0
	 */
	public static <V> Enumeration<? extends V> enumerate(V... array) {
		return new ArrayEnumerator<V>(array);
	}

	/**
	 * Iterate over the lines in a File.
	 * 
	 * @param file
	 *            The file.
	 * @return An Iterable which allows for iterating over the lines in a file.
	 * @throws RuntimeIOException
	 *             If the file can not be read.
	 * @since 1.0
	 */
	public static Iterable<String> lines(File file) {
		try {
			return new LineBasedFileIterator(file);
		} catch (FileNotFoundException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Iterate over the lines in a File, using the given Charset to read the
	 * contents of the file.
	 * 
	 * @param file
	 *            The file.
	 * @param charset
	 *            The Charset.
	 * @return An Iterable which allows for iterating over the lines in a file.
	 * @throws RuntimeIOException
	 *             If the file can not be read.
	 * @since 1.0
	 * 
	 * @see IOUtil For available Charset instances.
	 */
	public static Iterable<String> lines(File file, Charset charset) {
		try {
			return new LineBasedFileIterator(file, charset);
		} catch (FileNotFoundException exc) {
			throw new RuntimeIOException(exc);
		}
	}

	/**
	 * Returns the empty Iterator (an Iterator that does not return any
	 * elements). This is a method instead of a constant field, such you get
	 * generic types right without warning.
	 * 
	 * @return The empty Iterator.
	 */
	public static <T> Iterator<T> emptyIterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}

			@Override
			public void remove() {
				throw new IllegalStateException(
						"This iterator is empty and does not contain any elements.");
			}
		};
	}
}
