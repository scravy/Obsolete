package net.scravy.technetium.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;

class EnumerationIterator<T> implements Iterable<T>, Iterator<T> {

	private final Enumeration<T> enumeration;
	
	
	public EnumerationIterator(Enumeration<T> enumeration) {
		this.enumeration = enumeration;
		
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	@Override
	public T next() {
		return enumeration.nextElement();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
