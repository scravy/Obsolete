package net.scravy.technetium.util.function;

import java.util.Comparator;

/**
 * Utility classes and methods for working with Functions in Java. Note that
 * functions are <b>not</b> first class citizens in Java, thus the whole concept
 * is emulated via the {@link Function} and {@link Function2} interfaces.
 * 
 * @author Julian Fleischer
 * @experimental
 * @since 1.0
 */
public class FunctionUtil {

	FunctionUtil() {

	}

	/**
	 * Turns the specified function into a Comparator.
	 * 
	 * @param function
	 *            The function.
	 * @return A comparator using the specified function for comparisons.
	 * @since 1.0
	 */
	public static <T> Comparator<T> comparator(
			final Function2<T, T, Integer> function) {
		return new Comparator<T>() {

			@Override
			public int compare(T left, T right) {
				return function.apply(left, right);
			}

		};
	}

	/**
	 * Return the Id function for the given type T.
	 * 
	 * @return The Id function for the type T.
	 * @since 1.0
	 */
	public static <T> Function<T, T> id() {
		return new Id<T>();
	}

	/**
	 * Creates a new function from a function, with its arguments flipped.
	 * 
	 * @param function
	 *            The function.
	 * @return A function which does the same as the given function, but with
	 *         its arguments flipped.
	 * @since 1.0
	 */
	public static <A, B, R> Function2<B, A, R> flip(
			final Function2<A, B, R> function) {
		return new Flip<A, B, R>(function);
	}

	/**
	 * Composes the two functions into one (<code>g(f(x))</code>).
	 * 
	 * @param g
	 *            The second function (<code>g</code> in the example above).
	 * @param f
	 *            The first function (<code>f</code> in the example above).
	 * @return A function which is the composition <code>g &middot; f</code>.
	 * @since 1.0
	 */
	public static <A, B, C> Function<A, C> compose(final Function<B, C> g,
			final Function<A, B> f) {
		return new Function<A, C>() {
			@Override
			public C apply(A argument) {
				return g.apply(f.apply(argument));
			}
		};
	}

	/**
	 * Turns a function which returns a function into a single function with two
	 * arguments.
	 * 
	 * @param function
	 *            The function with one argument.
	 * @return A function with two arguments.
	 * @since 1.0
	 */
	public static <A, B, R> Function<A, Function<B, R>> curry(
			final Function2<A, B, R> function) {
		return new Function<A, Function<B, R>>() {
			@Override
			public Function<B, R> apply(final A firstArgument) {
				return new Function<B, R>() {
					@Override
					public R apply(B secondArgument) {
						return function.apply(firstArgument, secondArgument);
					}
				};
			}
		};
	}

	/**
	 * Turns a function with two arguments into a function with one argument
	 * returning another function with one argument. Useful for partial
	 * application of a function (you can create “sections” of a function).
	 * 
	 * @param function
	 *            The function with two arguments.
	 * @return A function with one argument that returns another function with
	 *         one argument.
	 * @since 1.0
	 */
	public static <A, B, R> Function2<A, B, R> uncurry(
			final Function<A, Function<B, R>> function) {
		return new Function2<A, B, R>() {
			@Override
			public R apply(A firstArgument, B secondArgument) {
				return function.apply(firstArgument).apply(secondArgument);
			}
		};
	}
}
