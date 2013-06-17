package net.scravy.technetium.util.value;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author scravy
 * 
 */
public class ArrayUtilTest {

	/**
	 * 
	 */
	@Test
	public void checkEndsWith() {
		Assert.assertTrue(ArrayUtil.endsWith(new String[] { "hallo", "welt" },
				"welt"));
		Assert.assertTrue(ArrayUtil.endsWith(new Integer[] { 1, 2, 3 }, 2, 3));
		Assert.assertFalse(ArrayUtil.endsWith(new Integer[] { 1, 2, 3 }, 1, 2,
				3, 4));
		Assert.assertFalse(ArrayUtil.endsWith(new Integer[] { 1, 2, 3 }, 5));
	}

	/**
	 * 
	 */
	@Test
	public void checkStartsWith() {
		Assert.assertTrue(ArrayUtil.startsWith(
				new String[] { "hallo", "welt" }, "hallo"));
		Assert.assertTrue(ArrayUtil.startsWith(new Integer[] { 1, 2, 3 }, 1, 2));
		Assert.assertFalse(ArrayUtil.startsWith(new Integer[] { 1, 2, 3 }, 1,
				2, 3, 4));
		Assert.assertFalse(ArrayUtil.startsWith(new Integer[] { 1, 2, 3 }, 8));
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkEndsWithArguments() {
		Assert.assertTrue(true);
		ArrayUtil.endsWith(null);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void checkStartWithArguments() {
		ArrayUtil.startsWith(null);
	}

}
