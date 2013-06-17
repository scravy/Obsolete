package net.scravy.technetium.util;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A Unit test for {@link TrieSet}.
 */
public class TrieSetTest {

	/**
	 * Test for {@link TrieSet#add(String)}.
	 */
	@Test
	public void testAdd() {
		Set<String> trie = new TrieSet();

		Assert.assertTrue(trie.isEmpty());
		Assert.assertEquals(0, trie.size());
		Assert.assertFalse(trie.contains("TrieSet"));

		trie.add("TrieSet");
		Assert.assertTrue(trie.contains("TrieSet"));
		Assert.assertFalse(trie.isEmpty());
		Assert.assertEquals(1, trie.size());
	}

	/**
	 * Test for {@link TrieSet#addAll(String...)}.
	 */
	@Test
	public void testAddAll() {
		TrieSet trie = new TrieSet();

		Assert.assertFalse(trie.contains("TrieSet"));
		Assert.assertFalse(trie.contains("TrieSetX"));
		Assert.assertFalse(trie.contains("Trie"));
		trie.addAll("TrieSet", "TrieSetX");
		Assert.assertTrue(trie.contains("TrieSetX"));
		Assert.assertTrue(trie.contains("TrieSet"));
		Assert.assertFalse(trie.contains("Trie"));
		Assert.assertFalse(trie.isEmpty());
		Assert.assertEquals(2, trie.size());
	}

	/**
	 * Test for {@link TrieSet#remove(Object)}.
	 */
	@Test
	public void testRemove() {
		Set<String> trie = new TrieSet("Alpha", "Bravo", "Binary",
				"Alphanumeric");

		Assert.assertTrue(trie.contains("Alpha"));
		Assert.assertTrue(trie.contains("Bravo"));
		Assert.assertTrue(trie.contains("Binary"));
		Assert.assertTrue(trie.contains("Alphanumeric"));
		Assert.assertEquals(4, trie.size());

		trie.remove("Alpha");
		Assert.assertFalse(trie.contains("Alpha"));
		Assert.assertEquals(3, trie.size());
		Assert.assertFalse(trie.isEmpty());
		Assert.assertTrue(trie.contains("Alphanumeric"));

		trie.remove("Bravo");
		Assert.assertFalse(trie.contains("Bravo"));
		Assert.assertEquals(2, trie.size());
		Assert.assertFalse(trie.isEmpty());

		trie.remove("Binary");
		Assert.assertFalse(trie.contains("Binary"));
		Assert.assertEquals(1, trie.size());
		Assert.assertFalse(trie.isEmpty());

		trie.remove("Alphanumeric");
		Assert.assertFalse(trie.contains("Alphanumeric"));
		Assert.assertEquals(0, trie.size());
		Assert.assertTrue(trie.isEmpty());
	}

}