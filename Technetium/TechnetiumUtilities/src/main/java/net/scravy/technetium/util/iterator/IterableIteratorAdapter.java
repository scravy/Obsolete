package net.scravy.technetium.util.iterator;

import java.util.Iterator;

/**
 * Adapter for implementing an IterableIterator. Implementations only need to
 * implement the two Iterator methods “next” and “hasNext”.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <T>
 *            The element in this Iterator.
 */
public abstract class IterableIteratorAdapter<T> implements Iterable<T>,
		Iterator<T> {

	@Override
	final public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	final public Iterator<T> iterator() {
		return this;
	}
}