package net.scravy.technetium.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class TrieSet extends AbstractSet<String> implements Set<String> {

	public TrieSet(String... words) {
		addAll(words);
	}

	public TrieSet(Iterable<String> words) {
		addAll(words);
	}

	static class Node {
		NavigableMap<Character, Node> children = new TreeMap<Character, Node>();

		boolean isWord = false;
	}

	private final Node root = new Node();
	private int size = 0;

	@Override
	public boolean add(String word) {
		if (word == null) {
			throw new IllegalArgumentException(
					"TrieSet.add(String) does not allow for null values.");
		}
		Node current = root;
		for (int i = 0; i < word.length(); i++) {
			Character letter = Character.valueOf(word.charAt(i));
			if (!current.children.containsKey(letter)) {
				current.children.put(letter, new Node());
			}
			current = current.children.get(letter);
		}
		if (!current.isWord) {
			size++;
			current.isWord = true;
			return true;
		}
		return false;
	}

	public boolean addAll(Iterable<String> words) {
		boolean added = false;
		for (String word : words) {
			if (add(word)) {
				added = true;
			}
		}
		return added;
	}

	public boolean addAll(String... words) {
		boolean added = false;
		for (String word : words) {
			if (add(word)) {
				added = true;
			}
		}
		return added;
	}

	@Override
	public boolean remove(Object object) {
		if (object == null) {
			return false;
		}
		final String word = object.toString();

		Node current = root;
		List<Node> path = new ArrayList<Node>(word.length() + 1);
		path.add(current);
		for (int i = 0; i < word.length(); i++) {
			Character letter = Character.valueOf(word.charAt(i));
			if (!current.children.containsKey(letter)) {
				return false;
			}
			current = current.children.get(letter);
			path.add(current);
		}
		if (!current.isWord) {
			return false;
		}
		current.isWord = false;
		for (int i = path.size() - 1; i >= 0; i--) {
			Node n = path.get(i);
			if (n.isWord) {
				break;
			} else if (n.children.isEmpty() && i > 0) {
				path.get(i - 1).children.remove(Character.valueOf(word
						.charAt(i - 1)));
			}
		}
		size--;
		return true;
	}

	@Override
	public boolean contains(Object object) {
		if (object == null) {
			return false;
		}
		final String word = object.toString();
		Node current = root;
		for (int i = 0; i < word.length(); i++) {
			Character letter = Character.valueOf(word.charAt(i));
			if (!current.children.containsKey(letter)) {
				return false;
			}
			current = current.children.get(letter);
		}
		return current.isWord;
	}

	@Override
	public Iterator<String> iterator() {
		return new TrieSetIterator(root);
	}

	@Override
	public int size() {
		return size;
	}

	public static void main(String... args) {
		TrieSet trie = new TrieSet("aaaa", "aaab", "aaacd", "aa", "beta",
				"bett");
		int i = 0;
		for (String x : trie) {
			i++;
			System.out.println(x);
			if (i > 10) {
				break;
			}
		}
	}

}