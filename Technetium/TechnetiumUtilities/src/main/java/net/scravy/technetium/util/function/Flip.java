package net.scravy.technetium.util.function;

/**
 * A wrapper around an existing {@link Function} that flips its arguments.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <A>
 *            The type of the first argument of the flipped function and of the
 *            second argument of the wrapped function.
 * @param <B>
 *            The type of the second argument of the flipped function and of the
 *            first argument of the wrapped function.
 * @param <R>
 *            The return type.
 */
public class Flip<A, B, R> implements Function2<B, A, R> {

	private final Function2<A, B, R> function;

	/**
	 * The constructor which takes the function which is to be wrapped.
	 * 
	 * @param function
	 *            The function which is to be wrapped by this Flip.
	 */
	public Flip(Function2<A, B, R> function) {
		this.function = function;
	}

	@Override
	public R apply(B firstArgument, A secondArgument) {
		return function.apply(secondArgument, firstArgument);
	}

}
