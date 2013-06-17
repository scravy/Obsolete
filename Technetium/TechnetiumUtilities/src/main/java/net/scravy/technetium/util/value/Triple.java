package net.scravy.technetium.util.value;

/**
 * A Triple.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <A>
 *            The type of the first component.
 * @param <B>
 *            The type of the second component.
 * @param <C>
 *            The type of the third component.
 */
public class Triple<A, B, C> {

	/**
	 * The first component of this Triple.
	 */
	public final A first;

	/**
	 * The second component of this Triple.
	 */
	public final B second;

	/**
	 * The third component of this Triple.
	 */
	public final C third;

	/**
	 * Standard constructor, takes the first and second component of this Tuple.
	 * 
	 * @param fst
	 *            The first component of this Triple (may be null).
	 * @param snd
	 *            The second component of this Triple (may be null).
	 * @param trd
	 *            The third component of this Triple (may be null).
	 */
	public Triple(A fst, B snd, C trd) {
		this.first = fst;
		this.second = snd;
		this.third = trd;
	}

	@Override
	public String toString() {
		return "(" + first.toString() + ", " + second.toString() + ", "
				+ third.toString() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
		return result;
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
		Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
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
		if (third == null) {
			if (other.third != null) {
				return false;
			}
		} else if (!third.equals(other.third)) {
			return false;
		}
		return true;
	}
}
