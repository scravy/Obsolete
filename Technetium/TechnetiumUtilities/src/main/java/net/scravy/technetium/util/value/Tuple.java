package net.scravy.technetium.util.value;

/**
 * A Tuple. Enhancement over a two element array: It is typed.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <A>
 *            The type of the first component.
 * @param <B>
 *            The type of the second component.
 */
public class Tuple<A, B> {

	/**
	 * The first component of this Tuple.
	 * 
	 * @since 1.0
	 */
	public final A first;

	/**
	 * The second component of this Tuple.
	 * 
	 * @since 1.0
	 */
	public final B second;

	/**
	 * Standard constructor, takes the first and second component of this Tuple.
	 * 
	 * @param fst
	 *            The first component of this Tuple (may be null).
	 * @param snd
	 *            The second component of this Tuple (may be null).
	 * 
	 * @since 1.0
	 */
	public Tuple(A fst, B snd) {
		this.first = fst;
		this.second = snd;
	}

	/**
	 * Creates a two-element object array from this tuple.
	 * 
	 * @return A two-element array containing {@link #first} at index 0 and
	 *         {@link #second} at index 1.
	 * 
	 * @since 1.0
	 */
	public Object[] toArray() {
		return new Object[] { first, second };
	}

	/**
	 * Stores the values from this tuple into the given array.
	 * 
	 * @param array
	 *            The array to store the values into.
	 * @return The given array with {@link #first} at index 0 and
	 *         {@link #second} at index 1.
	 * 
	 * @throws ClassCastException
	 *             If one of the elements in the tuple is not compatible with
	 *             the element type of the array.
	 * 
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] array) {
		if (array == null) {
			throw new IllegalArgumentException(
					"The given array must not be null.");
		}
		if (array.length < 2) {
			throw new IllegalArgumentException(
					"The given array must have at least the length 2 in" +
							" order to store a tuple into it.");
		}
		array[0] = (T) first;
		array[1] = (T) second;
		return array;
	}

	@Override
	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ")";
	}

	@Override
	public int hashCode() {
		return ((first == null) ? 0 : first.hashCode())
				* ((second == null) ? 0 : second.hashCode()) * 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tuple<?, ?> other = (Tuple<?, ?>) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}
}
