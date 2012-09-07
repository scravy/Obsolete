package net.abusingjava;
import java.util.Iterator;


public class OneValueIterator<V> implements Iterator<V> {
	
	V $value;
	
	public OneValueIterator(final V $value) {
		this.$value = $value;
	}
	
	@Override
	public boolean hasNext() {
		return $value != null;
	}
	
	@Override
	public V next() {
		V $next = $value;
		$value = null;
		return $next;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}