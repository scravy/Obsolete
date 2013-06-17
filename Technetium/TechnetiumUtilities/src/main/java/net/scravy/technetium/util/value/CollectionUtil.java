package net.scravy.technetium.util.value;

import java.util.Collection;

/**
 * Utility methods for working with Collections.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class CollectionUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	CollectionUtil() {
	}

	/**
	 * Adds all elements from the specified array to the specified collection.
	 * 
	 * @param collection
	 *            The Collection.
	 * @param values
	 *            The elements.
	 * @since 1.0
	 */
	public static <T, U extends T> void adådAll(Collection<T> collection,
			U... values) {
		for (U value : values) {
			collection.add(value);
		}
	}
}
