package net.scravy.technetium.util.iterator;


class ArrayIterator<T> extends IterableIteratorAdapter<T> {

	private final T[] array;
	int index = 0;
	
	public ArrayIterator(T[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return index + 1 < array.length;
	}

	@Override
	public T next() {
		return array[index++];
	}
	
}