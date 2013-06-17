package net.scravy.technetium.util.iterator;

import java.util.Iterator;

/**
 * A skeleton implementation for arbitrary Iterators. It implements remove() by
 * throws a UnsupportedOperationException. Do not use this class if you need
 * that functionality.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <T>
 *            The element type of this Iterator.
 */
public abstract class IteratorAdapter<T> implements Iterator<T> {

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}