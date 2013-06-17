package net.scravy.technetium.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Methods for working with special values such as <code>zero</code>, and
 * {@link Either}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class ValueUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	ValueUtil() {
	}

	/**
	 * Creates a Left Either object – see {@link Either}.
	 * 
	 * @param value
	 *            The value to wrap as the Left value.
	 * @return A Left Either object.
	 * @since 1.0
	 */
	public static <L, R> Either<L, R> left(L value) {
		return new Left<L, R>(value);
	}

	/**
	 * Creates a Right Either object – see {@link Either}.
	 * 
	 * @param value
	 *            The value to wrap as the Right value.
	 * @return A Right Either object.
	 * @since 1.0
	 */
	public static <L, R> Either<L, R> right(R value) {
		return new Right<L, R>(value);
	}

	/**
	 * Creates a {@link Tuple} from two values.
	 * 
	 * @param first
	 *            The first component of the tuple.
	 * @param second
	 *            The second component of the tuple.
	 * @return The tuple made up of the two components.
	 * @since 1.0
	 */
	public static <A, B> Tuple<A, B> tuple(A first, B second) {
		return new Tuple<A, B>(first, second);
	}

	/**
	 * Creates an array from the given values (useful as syntactic improvement
	 * only).
	 * 
	 * This method will create a generic array. In Java 6 this will induce a
	 * warning on the call-site, in Java 7 it will not.
	 * 
	 * @param value
	 *            A Varargs array of values.
	 * @return The array of values.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] array(T... value) {
		if (value == null) {
			return (T[]) new Object[0];
		}
		return value;
	}

	/**
	 * Checks if two values are equal according to their
	 * {@link Object#equals(Object) equals()} method.
	 * 
	 * It is noted in the documentation of this function that the left objects
	 * equal() method is being used. The fact whether the left or the right
	 * methods equals()-method is being used should not make a difference as to
	 * the definition of equals() (equals ought to be symmetric).
	 * 
	 * @param left
	 *            The left object. This methods equals() method will be used for
	 *            comparison.
	 * @param right
	 *            The right object.
	 * @return <code>true</code> if both objects are null or if both objects are
	 *         non-null and equal according to the left objects equals()-method.
	 *         <code>false</code> otherwise.
	 * @since 1.0
	 */
	public static boolean equals(Object left, Object right) {
		if (left == null && right == null) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		return left.equals(right);
	}

	/**
	 * Returns the given value if it is not null, or executes the given Callable
	 * and returns its return value instead.
	 * 
	 * @param value
	 *            The value to be checked for null.
	 * @param defaultValue
	 *            The callable which is executed if value is null.
	 * @return The value or the return value of the execution of defaultValue if
	 *         value is null.
	 * @throws IllegalArgumentException
	 *             If defaultValue is null.
	 * @throws Exception
	 *             If an exception occurs when calling {@link Callable#call()}
	 *             on defaultValue.
	 * @since 1.0
	 */
	public static <T> T maybe(T value, Callable<T> defaultValue)
			throws Exception {
		if (defaultValue == null) {
			throw new IllegalArgumentException(
					"The Callable<T> defaultValue must not be null.");
		}
		if (value == null) {
			return defaultValue.call();
		}
		return value;
	}

	/**
	 * Returns the given value if it is not null, the provided defaultValue
	 * otherwise.
	 * 
	 * You should not provide null as a defaultValue. However, this method will
	 * not throw an IllegalArgumentException if null is provided as
	 * defaultValue.
	 * 
	 * @param value
	 *            The value to be checked for null.
	 * @param defaultValue
	 *            The value to be used iff value is null.
	 * @return The value iff value is not null, the defaultValue iff value is
	 *         null. Returns null only iff value and the defaultValue are null.
	 * @since 1.0
	 */
	public static <T> T maybe(T value, T defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * Checks whether the value contains data.
	 * <p>
	 * Works with all auto-boxed primitive types, strings, and null-values.
	 * </p>
	 * 
	 * @param value
	 *            The value.
	 * @return true if this value is null, if value is a String and empty, if
	 *         value is a Number and equals zero, if value is the NUL-Character;
	 *         false otherwise.
	 * @since 1.0
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof Boolean) {
			return ((Boolean) value);
		} else if (value instanceof String) {
			return "".equals(value);
		} else if (value instanceof BigDecimal) {
			return BigDecimal.ZERO.equals(value);
		} else if (value instanceof BigInteger) {
			return BigInteger.ZERO.equals(value);
		} else if (value instanceof Double) {
			return ((Double) value).doubleValue() == 0;
		} else if (value instanceof Float) {
			return ((Float) value).doubleValue() == 0;
		} else if (value instanceof Number) {
			return ((Number) value).longValue() == 0L;
		} else if (value instanceof Character) {
			return ((Character) value).charValue() == '\0';
		}
		return false;
	}

	/**
	 * Calls the toString() method on an object, unless it is zero.
	 * 
	 * @param object
	 *            The object.
	 * @return The string representation according to the objects
	 *         toString()-method, or the empty string if <code>object</code> is
	 *         null.
	 * @since 1.0
	 */
	public static CharSequence toString(Object object) {
		if (object == null) {
			return "";
		}
		return object.toString();
	}
}
