package net.abusingjava.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.abusingjava.Author;
import net.abusingjava.Experimental;
import net.abusingjava.functions.Function;

@Author("Julian Fleischer")
@Experimental
public class TrieMapImpl<V> implements TrieMap<V> {

	class TrieMapElement {
		
		@SuppressWarnings("unchecked")
		TrieMapElement[] $children = (TrieMapElement[]) new Object[$chars.length];
		
		
	}
	
	int $size = 0;
	
	final char[] $chars;
	final TrieMapElement $root = new TrieMapElement();
	final Function<String,String> $normalizingFunction;
	
	public TrieMapImpl(final char[] $chars, final Function<String,String> $normalizingFunction) {
		this.$chars = $chars;
		this.$normalizingFunction = $normalizingFunction;
	}
	
	@Override
	public void clear() {
		for (int $i = 0; $i < $root.$children.length; $i++) {
			$root.$children[$i] = null;
		}
		$size = 0;
	}

	@Override
	public boolean containsKey(final Object $key) {
		return false;
	}

	@Override
	public boolean containsValue(final Object $value) {

		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, V>> entrySet() {

		return null;
	}

	@Override
	public V get(final Object key) {

		return null;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	@Override
	public Set<String> keySet() {

		return null;
	}

	@Override
	public V put(final String $key, final V value) {
		TrieMapElement $e = $root;
		final String $normalizedKey = $normalizingFunction.call($key);
		
		
		return null;
	}

	@Override
	public void putAll(final Map<? extends String, ? extends V> $m) {
		for (Entry<? extends String, ? extends V> $e : $m.entrySet()) {
			put($e.getKey(), $e.getValue());
		}
	}

	@Override
	public V remove(final Object key) {

		return null;
	}

	@Override
	public int size() {
		return $size;
	}

	@Override
	public Collection<V> values() {

		return null;
	}
	
}
