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
public abstract class GenericIterator<T> implements Iterator<T> {

	private T nextElement;

	private boolean init = false;

	/**
	 * No-arg default constructor.
	 * 
	 * @since 1.0
	 */
	public GenericIterator() {

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
		if (!init) {
			nextElement = nextElement();
			init = true;
		}
		T result = nextElement;
		nextElement = nextElement();
		return result;
	}

	@Override
	final public boolean hasNext() {
		if (!init) {
			nextElement = nextElement();
			init = true;
		}
		return nextElement != null;
	}

	@Override
	final public void remove() {
		throw new UnsupportedOperationException();
	}
}