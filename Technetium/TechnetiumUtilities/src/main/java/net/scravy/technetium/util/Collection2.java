package net.scravy.technetium.util;

import java.util.Collection;
import java.util.Random;

/**
 * A richer {@link Collection} interface.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <E>
 *            The type of the elements in this Collection.
 */
public interface Collection2<E> extends Collection<E> {

	/**
	 * Add all elements from the given Iterable.
	 * 
	 * @param iterable
	 *            The Collection.
	 * @return Whether this collection has been changed due to the add operation
	 *         or not.
	 * @since 1.0
	 */
	boolean addAll(Iterable<? extends E> iterable);

	/**
	 * Pick a value randomly.
	 * 
	 * @param random
	 *            The random generator.
	 * @since 1.0
	 * @return A random value from this Collection.
	 */
	E rand(Random random);

	/**
	 * Pick a value randomly, using the specified random generator.
	 * 
	 * @since 1.0
	 * @return A random value from this Collection.
	 */
	E rand();

	/**
	 * Add all elements from the given array.
	 * 
	 * @param elements
	 *            The elements.
	 * @return Whether this collection has been changed due to the add operation
	 *         or not.
	 * @since 1.0
	 */
	boolean addAll(E... elements);

}