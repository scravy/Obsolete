package net.scravy.technetium.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.scravy.technetium.util.TrieSet.Node;

public class TrieSetIterator implements Iterator<String> {

	List<Entry<Character, Node>> path = new ArrayList<Entry<Character, Node>>();
	StringBuilder stringBuilder = new StringBuilder();
	String next;

	TrieSetIterator(Node root)
	{
		if (!root.children.isEmpty()) {
			Node current = root;
			while (!current.isWord) {
				Entry<Character, Node> entry =
						current.children.firstEntry();

				stringBuilder.append(entry.getKey());
				path.add(entry);

				current = entry.getValue();
			}
		}
		next = stringBuilder.toString();
		stringBuilder.setLength(0);
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public String next() {
		String returnValue = next;

		next = null;

		Entry<Character, Node> current = path.get(path.size() - 1);
		if (current.getValue().children.isEmpty()) {

		} else {
			current = current.getValue().children.firstEntry();
			path.add(current);
			while (!current.getValue().isWord) {
				current = current.getValue().children.firstEntry();
				path.add(current);
			}

			for (Entry<Character, Node> e : path) {
				stringBuilder.append(e.getKey());
			}
			next = stringBuilder.toString();
			stringBuilder.setLength(0);
		}

		return returnValue;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}