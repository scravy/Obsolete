package net.abusingjava.collections;

import java.util.Map;
import java.util.Set;

import net.abusingjava.Author;
import net.abusingjava.Since;
import net.abusingjava.Version;

/**
 * A convenient interface to a <code>Map&lt;K, Set&lt;V>></code>.
 */
@Author("Julian Fleischer")
@Version("2012-01-05")
@Since("2012-01-05")
public interface SetMap<K, V> extends Map<K, Set<V>> {

	/**
	 * Adds a $value to the Set associated with the given $key.
	 */
	void add(K $key, V $value);
	
	/**
	 * Adds all $key/$value sets to this SetMap.
	 */
	void addAll(SetMap<K, V> $map);
	
	/**
	 * 
	 * All all $values to the $key.
	 */
	void addAll(K $key, Iterable<V> $values);
	
	/**
	 * All add $values to the $key.
	 */
	void addAll(K $key, V[] $values);

	/**
	 * Checks whether the $value is associated with the given $key or not.
	 * 
	 * @return true iff <code>valuesForKey($key).contains($value)</code>, false
	 *         otherwise.
	 */
	boolean has(K $key, V $value);

	/**
	 * Checks whether the $key is registered in this SetMap or not.
	 */
	boolean hasKey(K $key);

	void remove(K $key, V $value);

	void removeValues(K $key);

	Set<V> valuesForKey(K $key);

	int getNumberOfKeys();

	int getNumberOfValues();
	
	int getNumberOfValues(K $key);
}
