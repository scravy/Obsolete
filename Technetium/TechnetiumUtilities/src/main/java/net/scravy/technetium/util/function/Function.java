package net.scravy.technetium.util.function;

/**
 * A function which takes one argument.
 * 
 * @author Julian Fleischer
 * 
 * @param <A>
 *            The type of the argument.
 * @param <R>
 *            The return type of this function.
 */
public interface Function<A, R> {

	/**
	 * Apply this function to the given argument.
	 * 
	 * @param argument
	 *            The argument.
	 * @return Whatever this function returns.
	 */
	R apply(A argument);

}
