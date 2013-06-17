package net.scravy.technetium.util.value;

/**
 * A Left {@link Either} object.
 * 
 * @author Julian Fleischer
 * 
 * @param <L>
 *            The Left type. See {@link Either} for further explanation.
 * @param <R>
 *            The Right type. See {@link Either} for further explanation.
 */
public class Left<L, R> implements Either<L, R> {

	L value;

	/**
	 * Constructs a new Either object of the Left type L. Use of this
	 * constructor is discouraged, use {@link ValueUtil#left(Object)}.
	 * 
	 * @param value
	 *            The wrapped value. Must not be null.
	 * @throws IllegalArgumentException
	 *             iff the given value is <code>null</code>.
	 */
	public Left(L value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"`value` must not be null (use Maybe if you want to distinguish something form null).");
		}
		this.value = value;
	}

	@Override
	public boolean isLeft() {
		return true;
	}

	@Override
	public boolean isRight() {
		return false;
	}

	@Override
	public L getLeft() {
		return value;
	}

	@Override
	public R getRight() {
		throw new IllegalStateException(
				"Left.getRight() called (should be checked using isRight() previously)");
	}

}
