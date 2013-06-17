package net.scravy.technetium.util.iterator;

import java.util.Iterator;

/**
 * A generic implementation for Iterators.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * @param <T>
 *            The element type of this Iterator.
 */
public abstract class GenericIterableIterator<T>
		implements Iterable<T>, Iterator<T> {

	private T nextElement;

	/**
	 * No-arg default constructor.
	 * 
	 * @since 1.0
	 */
	public GenericIterableIterator() {
		nextElement = nextElement();
	}

	/**
	 * Return the next element in this iterator.
	 * <p>
	 * Implementations only need to implement this single method.
	 * Implementations should keep the method protected.
	 * </p>
	 * 
	 * @return The next element in this iterator or null, if there is no such
	 *         Element.
	 */
	abstract protected T nextElement();

	@Override
	final public T next() {
		T result = nextElement;
		nextElement = nextElement();
		return result;
	}

	@Override
	final public boolean hasNext() {
		return nextElement != null;
	}

	@Override
	final public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	final public Iterator<T> iterator() {
		return this;
	}
}