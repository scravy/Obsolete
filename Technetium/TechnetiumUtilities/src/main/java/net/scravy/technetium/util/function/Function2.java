package net.scravy.technetium.util.function;

/**
 * A function which takes one argument.
 * 
 * @author Julian Fleischer
 * 
 * @param <A>
 *            The type of the first argument.
 * @param <B>
 *            The type of the second argument.
 * @param <R>
 *            The return type of this function.
 */
public interface Function2<A, B, R> {

	/**
	 * Apply this function to the given arguments.
	 * 
	 * @param firstArgument
	 *            The first argument.
	 * @param secondArgument
	 *            The second argument.
	 * @return Whatever this function returns.
	 */
	R apply(A firstArgument, B secondArgument);

}
