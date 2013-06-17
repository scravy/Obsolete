package net.scravy.weblet.forms;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class PathTrie<V> extends AbstractMap<String[], V> {

	final static StringArrayComparator stringArrayComparator = new StringArrayComparator();

	static class StringArrayComparator implements Comparator<String[]> {
		@Override
		public int compare(String[] left, String[] right) {
			for (int i = 0; i < Math.min(left.length, right.length); i++) {
				int result = left[i].compareTo(right[i]);
				if (result != 0) {
					return result;
				}
			}
			return left.length - right.length;
		}
	}

	static class Item<V> {

		Map<String, Item<V>> map = new HashMap<String, Item<V>>();
		Map<String[], Item<V>> wildcards = new TreeMap<String[], Item<V>>(
				stringArrayComparator);

		boolean any = false;

		V value = null;
	}

	V defaultValue = null;
	Item<V> root = new Item<V>();

	TreeMap<String[], V> entries = new TreeMap<String[], V>(
			stringArrayComparator);

	public void setDefaultValue(V value) {
		defaultValue = value;
	}

	public V put(String[] path, V value) {
		Item<V> cur = root;

		entries.put(path, value);

		for (int i = 0; i < path.length; i++) {
			if ("**".equals(path[i])) {
				cur.any = true;
				V oldValue = cur.value;
				cur.value = value;
				return oldValue;
			}
			Item<V> next;
			if (path[i].contains("*")) {
				String[] wildcard = new String[] {
						path[i].substring(0, path[i].indexOf('*')),
						path[i].substring(path[i].lastIndexOf('*') + 1) };
				next = cur.wildcards.get(wildcard);
				if (next == null) {
					next = new Item<V>();
					cur.wildcards.put(wildcard, next);
				}
			} else {
				next = cur.map.get(path[i]);
				if (next == null) {
					next = new Item<V>();
					cur.map.put(path[i], next);
				}
			}
			cur = next;
		}

		V oldValue = cur.value;
		cur.value = value;
		return oldValue;
	}

	public V get(String[] path) {
		Item<V> cur = root;

		for (int i = 0; i < path.length; i++) {
			if (cur.any) {
				return cur.value;
			}

			Item<V> next = cur.map.get(path[i]);
			if (next == null) {
				for (Entry<String[], Item<V>> e : cur.wildcards.entrySet()) {
					String[] wildcard = e.getKey();
					if (path[i].length() > wildcard[0].length()
							+ wildcard[1].length()) {
						if (path[i].startsWith(wildcard[0])
								&& path[i].endsWith(wildcard[1])) {
							next = e.getValue();
							break;
						}
					}
				}
				if (next == null) {
					return defaultValue;
				}
			}
			cur = next;
		}
		return cur.value == null ? defaultValue : cur.value;
	}

	@Override
	public Set<Entry<String[], V>> entrySet() {
		return entries.entrySet();
	}
}
