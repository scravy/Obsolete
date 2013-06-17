package net.scravy.technetium.util.data;

import java.util.Comparator;

/**
 * A Comparator that can compare anything.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class AnyComparator implements Comparator<Object> {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Object left, Object right) {
		if (left == null) {
			if (right == null) {
				return 0;
			}
			return Integer.MAX_VALUE;
		}
		if (right == null) {
			return Integer.MIN_VALUE;
		}
		if (left.getClass() != right.getClass()) {
			return left.getClass().getName()
					.compareTo(right.getClass().getName());
		}
		if (left instanceof Comparable) {
			return ((Comparable<Object>) left).compareTo(right);
		}
		int hashCodeLeft = left.hashCode();
		int hashCodeRight = right.hashCode();
		if (hashCodeLeft != hashCodeRight) {
			return hashCodeLeft - hashCodeRight;
		}
		return left.toString().compareTo(right.toString());
	}

}