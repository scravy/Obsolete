package net.scravy.technetium.util.function;

import java.util.Comparator;

/**
 * A ComparatorFunction is a Function which at the sime time implements the
 * comparator interface.
 * 
 * @see ComparatorFunctionAdapter
 * 
 * @author Julian Fleischer
 * 
 * @param <T>
 *            The type which is compared by this ComparatorFunction.
 * @since 1.0
 */
public interface ComparatorFunction<T> extends Comparator<T>,
		Function2<T, T, Integer> {

}