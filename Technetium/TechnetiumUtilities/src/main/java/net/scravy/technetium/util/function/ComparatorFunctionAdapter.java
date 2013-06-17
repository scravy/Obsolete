package net.scravy.technetium.util.function;

/**
 * Use this to create a {@link ComparatorFunction}. Implementations do need to
 * override either {@link #compare(Object, Object)} or
 * {@link #apply(Object, Object)}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <T>
 *            The type which is compared by this ComparatorFunction.
 */
public class ComparatorFunctionAdapter<T> implements ComparatorFunction<T> {

	@Override
	public int compare(T left, T right) {
		return apply(left, right);
	}

	@Override
	public Integer apply(T firstArgument, T secondArgument) {
		return compare(firstArgument, secondArgument);
	}

}