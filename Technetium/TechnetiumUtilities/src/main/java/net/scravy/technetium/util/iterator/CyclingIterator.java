package net.scravy.technetium.util.iterator;

import java.util.Iterator;
import java.util.List;

class CyclingIterator<T> implements Iterable<T>, Iterator<T> {

	final List<T> list;
	int index = 0;

	public CyclingIterator(List<T> list) {
		this.list = list;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public T next() {
		T value = list.get(index);
		index = (index + 1) % list.size();
		return value;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"The Cycle-Iterator repeats a list infinitely often, can not remove something from it.");
	}

}
