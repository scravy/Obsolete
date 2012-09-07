package net.abusingjava.collections;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class AbusingCollections {

	public static <K, V> Map.Entry<K, V> entry(K $key, V $value) {
		return new AbstractMap.SimpleImmutableEntry<K, V>($key, $value);
	}

	public static <K, V> SetMap<K, V> createSetMap() {
		return new SetMapImpl<K, V>();
	}
	
	public static <K, V> SetMap<K, V> createSetMap(Map.Entry<K, V>... $entries) {
		SetMap<K, V> $map = createSetMap();
		// TODO: Add entries
		return $map;
	}

	public static <K, V> HashMap<K, V> createHashMap() {
		return new HashMap<K, V>();
	}
	
	public static <K, V> HashMap<K, V> createHashMap(
			Map.Entry<K, V>... $entries) {
		HashMap<K, V> $map = createHashMap();
		for (Map.Entry<K, V> $entry : $entries) {
			$map.put($entry.getKey(), $entry.getValue());
		}
		return $map;
	}

	public static <K extends Comparable<? super K>, V> TreeMap<K, V> createTreeMap() {
		return new TreeMap<K, V>();
	}
	
	public static <K extends Comparable<? super K>, V> TreeMap<K, V> createTreeMap(
			Map.Entry<K, V>... $entries) {
		TreeMap<K, V> $map = createTreeMap();
		for (Map.Entry<K, V> $entry : $entries) {
			$map.put($entry.getKey(), $entry.getValue());
		}
		return $map;
	}

	public static <K> HashSet<K> createHashSet(K... $values) {
		HashSet<K> $hashSet = new HashSet<K>();
		for (K $value : $values) {
			$hashSet.add($value);
		}
		return $hashSet;
	}

	public static <K> HashSet<K> createHashSet(Iterable<K> $values) {
		HashSet<K> $hashSet = new HashSet<K>();
		for (K $value : $values) {
			$hashSet.add($value);
		}
		return $hashSet;
	}

	public static <K> TreeSet<K> createTreeSet(K... $values) {
		TreeSet<K> $treeSet = new TreeSet<K>();
		for (K $value : $values) {
			$treeSet.add($value);
		}
		return $treeSet;
	}

	public static <K> TreeSet<K> createTreeSet(Iterable<K> $values) {
		TreeSet<K> $treeSet = new TreeSet<K>();
		for (K $value : $values) {
			$treeSet.add($value);
		}
		return $treeSet;
	}

	@SuppressWarnings("unchecked")
	public static <A, B> LinkedList<B> convert(LinkedList<A> $listA,
			Class<B> $classB) {
		LinkedList<B> $listB = new LinkedList<B>();
		for (A $a : $listA) {
			if ($a != null && $classB.isAssignableFrom($a.getClass())) {
				$listB.add((B) $a);
			}
		}
		return $listB;
	}

	@SuppressWarnings("unchecked")
	public static <A, B> ArrayList<B> convert(List<A> $listA, Class<B> $classB) {
		ArrayList<B> $listB = new ArrayList<B>($listA.size());
		for (A $a : $listA) {
			if ($a != null && $classB.isAssignableFrom($a.getClass())) {
				$listB.add((B) $a);
			}
		}
		return $listB;
	}
}
