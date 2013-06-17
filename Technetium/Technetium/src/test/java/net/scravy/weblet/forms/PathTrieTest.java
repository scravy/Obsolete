package net.scravy.weblet.forms;

import net.scravy.weblet.forms.PathTrie;

import org.junit.Assert;
import org.junit.Test;

public class PathTrieTest {

	@Test
	public void testAddAndGet() {
		PathTrie<String> tree = new PathTrie<String>();
		
		tree.put(new String[] {"usr", "local", "lib"}, "libraries");
		Assert.assertEquals("libraries", tree.get(new String[] {"usr", "local", "lib"}));
		Assert.assertNull(tree.get(new String[] {"usr", "local"}));
		Assert.assertNull(tree.get(new String[] {"usr", "local", "lib", "X11"}));
	}
	
	@Test
	public void testAddAndGetSimpleAnyPath() {
		PathTrie<String> tree = new PathTrie<String>();
		
		tree.put(new String[] {"etc", "**"}, "something");
		Assert.assertEquals("something", tree.get(new String[] {"etc"}));
		Assert.assertEquals("something", tree.get(new String[] {"etc", "init.d"}));
		Assert.assertEquals("something", tree.get(new String[] {"etc", "init.d", "conf"}));
	}

	@Test
	public void testAddAndGetWildCard() {
		PathTrie<String> tree = new PathTrie<String>();
		
		tree.put(new String[] {"*.css"}, "style");
		tree.put(new String[] {"style", "*.css"}, "fancy");
		tree.put(new String[] {"~*", "*.xml"}, "square");
		Assert.assertEquals("style", tree.get(new String[] {"hello.css"}));
		Assert.assertEquals("fancy", tree.get(new String[] {"style", "world.css"}));
		Assert.assertEquals("square", tree.get(new String[] {"~page", "info.xml"}));
		Assert.assertNull(tree.get(new String[] {"scripts", "world.css"}));
	}

	@Test
	public void testEmptyPath() {
		PathTrie<String> tree = new PathTrie<String>();
		
		tree.put(new String[0], "yapp");
		Assert.assertEquals("yapp", tree.get(new String[0]));
	}
}
