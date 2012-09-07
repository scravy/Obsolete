package net.abusingjava.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class SetMapImpl<K, V> implements SetMap<K, V> {

	private int $count = 0;
	private final Map<K, Set<V>> $map = new HashMap<K, Set<V>>();

	@Override
	public void add(final K $key, final V $value) {
		Set<V> $target;
		if (!$map.containsKey($key)) {
			$target = new HashSet<V>();
			$map.put($key, $target);
		} else {
			$target = $map.get($key);
		}
		if (!$target.contains($value)) {
			$target.add($value);
			$count++;
		}
	}

	@Override
	public boolean has(final K $key, final V $value) {
		if (containsKey($key)) {
			return $map.get($key).contains($value);
		}
		return false;
	}

	@Override
	public boolean hasKey(final K $key) {
		return $map.containsKey($key);
	}
	
	@Override
	public boolean containsKey(final Object $key) {
		return $map.containsKey($key);
	}

	@Override
	public void remove(final K $key, final V $value) {
		if (containsKey($key)) {
			$count--;
			$map.get($key).remove($value);
		}
	}

	@Override
	public void removeValues(final K $key) {
		if (containsKey($key)) {
			$count -= $map.get($key).size();
			$map.get($key).clear();
			$map.remove($key);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<V> valuesForKey(final K $key) {
		if (containsKey($key)) {
			return Collections.unmodifiableSet($map.get($key));
		}
		return Collections.EMPTY_SET;
	}

	@Override
	public int getNumberOfKeys() {
		return $map.size();
	}

	@Override
	public int getNumberOfValues() {
		return $count;
	}

	@Override
	public int getNumberOfValues(final K $key) {
		if (containsKey($key)) {
			return $map.get($key).size();
		}
		return 0;
	}

	public Set<K> getKeys() {
		return $map.keySet();
	}

	@Override
	public void addAll(final SetMap<K, V> $map) {
		for (Entry<K, Set<V>> $e : $map.entrySet()) {
			addAll($e.getKey(), $e.getValue());
		}
	}

	@Override
	public void addAll(final K $key, final Iterable<V> $values) {
		for (V $value : $values) {
			add($key, $value);
		}
	}

	@Override
	public void addAll(final K $key, final V[] $values) {
		for (V $value : $values) {
			add($key, $value);
		}
	}

	@Override
	public void clear() {
		$map.clear();
	}

	@Override
	public boolean containsValue(final Object $value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<K, Set<V>>> entrySet() {
		return $map.entrySet();
	}

	@Override
	public Set<V> get(final Object $key) {
		return $map.get($key);
	}

	@Override
	public boolean isEmpty() {
		return $map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return $map.keySet();
	}

	@Override
	public Set<V> put(final K key, final Set<V> value) {
		return null;
	}

	@Override
	public void putAll(final Map<? extends K, ? extends Set<V>> m) {
		
	}

	@Override
	public Set<V> remove(final Object $key) {
		return $map.remove($key);
	}

	@Override
	public int size() {
		return $map.size();
	}

	@Override
	public Collection<Set<V>> values() {
		return $map.values();
	}
	
	@Override
	public int hashCode() {
		return $map.hashCode();
	}
	
	@Override
	public boolean equals(final Object $object) {
		return $map.equals($object);
	}
	
	
}
