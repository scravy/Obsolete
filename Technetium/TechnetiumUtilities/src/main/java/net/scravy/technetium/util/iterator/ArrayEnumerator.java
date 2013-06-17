package net.scravy.technetium.util.iterator;

import java.util.Enumeration;

class ArrayEnumerator<T> implements Enumeration<T> {

	private final T[] array;
	int index = 0;
	
	public ArrayEnumerator(T[] array) {
		this.array = array;
	}

	@Override
	public boolean hasMoreElements() {
		return index + 1 < array.length;
	}

	@Override
	public T nextElement() {
		return array[index++];
	}
	
}