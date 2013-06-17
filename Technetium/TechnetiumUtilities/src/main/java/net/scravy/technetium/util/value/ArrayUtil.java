package net.scravy.technetium.util.value;

/**
 * Utility methods for working with arrays.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class ArrayUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	ArrayUtil() {
	}

	/**
	 * Checks whether two arrays are equal. This function only checks for the
	 * contents of the arrays to be the same, that is, a <code>Number[]</code>
	 * array containing only Integer-objects and an <code>Integer[]</code> array
	 * may be judged as equal.
	 * <p>
	 * The second parameter is a varargs parameter, making it particularly easy
	 * to check a given array against some values:
	 * </p>
	 * 
	 * <pre></pre>
	 * 
	 * 
	 * @param left
	 *            The left array
	 * @param right
	 *            The right array (varargs)
	 * @return true IFF the length of both arrays is the same AND
	 *         <code>TypeUtil.equals(left[i], right[i])</code> is true for every
	 *         i < length. false otherwise.
	 * @throws IllegalArgumentException
	 *             If either of the arrays is null.
	 */
	public static boolean equals(Object[] left, Object... right) {
		if (left == null || right == null) {
			throw new IllegalArgumentException(
					"Neither left array nor right array may be null.");
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; i++) {
			if (!ValueUtil.equals(left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the first elements in an array are the same as the given
	 * prefix.
	 * 
	 * @param array
	 *            The array to be checked.
	 * @param prefix
	 *            The prefix used for comparison.
	 * @return <code>true</code> if the elements 0 to i of the array are the
	 *         same as the elements 0 to i in the prefix, where i is the length
	 *         of the prefix minus 1; <code>false</code> otherwise.
	 */
	public static boolean startsWith(Object[] array, Object... prefix) {
		if (array == null || prefix == null) {
			throw new IllegalArgumentException(
					"Neither array nor prefix may be null.");
		}
		if (prefix.length > array.length) {
			return false;
		}
		for (int i = 0; i < prefix.length; i++) {
			if (!ValueUtil.equals(array[i], prefix[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the last elements in an array are the same as the given
	 * suffix.
	 * 
	 * @param array
	 *            The array to be checked.
	 * @param suffix
	 *            The prefix used for comparison.
	 * @return <code>true</code> if the elements (n minus i) to (n minus 1) of
	 *         the array are the same as the elements 0 to i in the prefix,
	 *         where i is the length of the suffix minus 1; <code>false</code>
	 *         otherwise.
	 */
	public static boolean endsWith(Object[] array, Object... suffix) {
		if (array == null || suffix == null) {
			throw new IllegalArgumentException(
					"Neither array nor suffix may be null.");
		}
		if (suffix.length > array.length) {
			return false;
		}
		for (int i = 1; i <= suffix.length; i++) {
			if (!ValueUtil
					.equals(array[array.length - i], suffix[suffix.length - i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether a value is contained in a given array.
	 * 
	 * @param haystack
	 *            The array.
	 * @param needle
	 *            The value which may or may not be in the array.
	 * @return True iff <code>TypeUtil.equals(needle, haystack[i])</code> for
	 *         any <code>i < array.length</code>.
	 * @throws IllegalArgumentException
	 *             If haystack is null.
	 */
	public static int indexOf(Object[] haystack, Object needle) {
		if (haystack == null) {
			throw new IllegalArgumentException("`haystack` may not be null.");
		}
		if (needle == null) {
			for (int i = 0; i < haystack.length; i++) {
				if (haystack[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < haystack.length; i++) {
				if (needle.equals(haystack[i])) {
					return i;
				}
			}
		}
		return -1;
	}
}
