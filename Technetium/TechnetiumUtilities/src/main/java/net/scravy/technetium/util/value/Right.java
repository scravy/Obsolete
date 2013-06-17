package net.scravy.technetium.util.value;
/**
 * A Right {@link Either} object.
 * 
 * @author Julian Fleischer
 * 
 * @param <L>
 *            The Left type. See {@link Either} for further explanation.
 * @param <R>
 *            The Right type. See {@link Either} for further explanation.
 */
public class Right<L, R> implements Either<L, R> {

	R value;

	/**
	 * Constructs a new Either object of the Right type R. Use of this
	 * constructor is discouraged, use {@link ValueUtil#left(Object)}.
	 * 
	 * @param value
	 *            The wrapped value. Must not be null.
	 * @throws IllegalArgumentException
	 *             iff the given value is <code>null</code>.
	 */
	public Right(R value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"`value` may not be null (use Maybe if you want to distinguish something form null).");
		}
		this.value = value;
	}

	@Override
	public boolean isLeft() {
		return false;
	}

	@Override
	public boolean isRight() {
		return true;
	}

	@Override
	public L getLeft() {
		throw new IllegalStateException(
				"Right.getLeft() called (should be checked using isLeft() previously)");
	}

	@Override
	public R getRight() {
		return value;
	}

}
