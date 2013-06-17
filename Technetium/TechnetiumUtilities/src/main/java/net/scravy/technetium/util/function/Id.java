package net.scravy.technetium.util.function;

/**
 * The Identity function. The identity function returns its input.
 * 
 * @author Julian Fleischer
 * 
 * @param <A>
 *            The argument and return type of this function.
 */
public class Id<A> implements Function<A, A> {

	@Override
	public A apply(A argument) {
		return argument;
	}

}
