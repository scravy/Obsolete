package net.abusingjava.collections;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import net.abusingjava.Author;
import net.abusingjava.Experimental;
import net.abusingjava.Since;
import net.abusingjava.Version;

@Experimental
@Author("Julian Fleischer")
@Version("2011-12-28")
@Since("2011-12")
public class CacheMapImpl<K, V> implements Map<K, V> {

	private final LinkedList<AbstractMap.SimpleEntry<K, V>> $list = new LinkedList<AbstractMap.SimpleEntry<K, V>>();

	
	@Override
	public V get(final Object $key) {
		V $v = null;
		AbstractMap.SimpleEntry<K, V> $e = null;
		Iterator<AbstractMap.SimpleEntry<K, V>> $it = $list.listIterator();
		while ($it.hasNext()) {
			$e = $it.next();
			if ($e.getKey().equals($key)) {
				$it.remove();
				$v = $e.getValue();
				break;
			}
		}
		if ($v != null) {
			$list.push($e);
			return $v;
		}
		return null;
	}

	@Override
	public V put(final K $key, final V $value) {
		V $v = get($key);
		if ($v != null) {
			$list.removeFirst();
		}
		$list.push(new AbstractMap.SimpleEntry<K, V>($key, $value));
		return $v;
	}

	public void trimToSize(final int $size) {
		while ($list.size() > $size) {
			$list.removeLast();
		}
	}

	@Override
	public void clear() {
		$list.clear();
	}

	@Override
	public boolean containsKey(final Object $key) {
		for (AbstractMap.SimpleEntry<K, V> $e : $list) {
			if ($e.getKey().equals($key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(final Object $value) {
		for (AbstractMap.SimpleEntry<K, V> $e : $list) {
			if ($e.getValue().equals($value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return new HashSet<Map.Entry<K, V>>($list);
	}

	@Override
	public boolean isEmpty() {
		return $list.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		// FIXME: Optimization
		Set<K> $keySet = new HashSet<K>();
		for (AbstractMap.SimpleEntry<K, V> $e : $list) {
			$keySet.add($e.getKey());
		}
		return $keySet;
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> $entries) {
		// FIXME
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(final Object $key) {
		AbstractMap.SimpleEntry<K, V> $e;
		Iterator<AbstractMap.SimpleEntry<K, V>> $it = $list.listIterator();
		while ($it.hasNext()) {
			$e = $it.next();
			if ($e.getKey().equals($key)) {
				$it.remove();
				return $e.getValue();
			}
		}
		return null;
	}

	@Override
	public int size() {
		return $list.size();
	}

	@Override
	public Collection<V> values() {
		// FIXME: Optimization
		ArrayList<V> $values = new ArrayList<V>($list.size());
		for (AbstractMap.SimpleEntry<K, V> $e : $list) {
			$values.add($e.getValue());
		}
		return $values;
	}
	
	public static void main(final String... $args) {
		CacheMapImpl<String, String> $map = new CacheMapImpl<String,String>();
		$map.put("one", "1");
		$map.put("two", "2");
		$map.put("three", "3");
		System.out.println($map.get("two"));
		$map.trimToSize(2);
		System.out.println($map.values());
		System.out.println($map.get("three"));
		$map.trimToSize(1);
		System.out.println($map.values());
	}
}
