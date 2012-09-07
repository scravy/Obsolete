package net.abusingjava.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ArraySetList<T> extends ArrayList<T> implements Set<T> {

	public boolean add(T $thing) {
		if (!contains($thing)) {
			super.add($thing);
		}
		return true;
	}
	
	public boolean add(Collection<T> $things) {
		for (T $thing : $things) {
			add($thing);
		}
		return true;
	}
	
}
