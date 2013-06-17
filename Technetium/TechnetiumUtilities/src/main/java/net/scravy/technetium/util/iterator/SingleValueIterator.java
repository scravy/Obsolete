package net.scravy.technetium.util.iterator;
import java.util.Iterator;


class SingleValueIterator<V> implements Iterator<V> {
	
	V value;
	
	public SingleValueIterator(final V value) {
		this.value = value;
	}
	
	@Override
	public boolean hasNext() {
		return value != null;
	}
	
	@Override
	public V next() {
		V next = value;
		value = null;
		return next;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}