package net.abusingjava.io;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.abusingjava.Author;
import net.abusingjava.Version;

import org.junit.Assert;
import org.junit.Test;

@Author("Julian Fleischer")
@Version("2011-01-11")
public class SerializerTest {

	@Test
	public void testDate() {
		Date $now = new Date();
		
		String $serialized = AbusingIO.serialize($now);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Date);
		Assert.assertEquals($now, $unserialized);
	}

	@Test
	public void testByte() {
		byte $value = Byte.MAX_VALUE;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Byte);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testShort() {
		short $value = Short.MAX_VALUE;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Short);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testInteger() {
		int $value = Integer.MAX_VALUE;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Integer);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testLong() {
		long $value = Long.MAX_VALUE;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Long);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testFloat() {
		float $value = Float.MAX_VALUE;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Float);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testDouble() {
		double $value = Math.PI;
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Double);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testString() {
		String $value = "simpleString";
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof String);
		Assert.assertEquals($value, $unserialized);
	}

	@Test
	public void testComplexString() {
		String $value = "This is a \"rather complicated\" string.\nYes it is.";
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);

		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof String);
		Assert.assertEquals($value, $unserialized);
	}
	
	@Test
	public void testCharacter() {
		char $value = 'ä';
		
		String $serialized = AbusingIO.serialize($value);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Character);
		Assert.assertEquals($value, $unserialized);
	}
	
	@Test
	public void testList() {
		List<Integer> $list = new LinkedList<Integer>();
		$list.add(3);
		$list.add(1334);
		$list.add(-42);
		
		String $serialized = AbusingIO.serialize($list);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof List);
		Assert.assertEquals($list, $unserialized);
	}

	@Test
	public void testSet() {
		Set<Integer> $set = new HashSet<Integer>();
		$set.add(3);
		$set.add(1334);
		$set.add(-42);
		
		String $serialized = AbusingIO.serialize($set);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof Set);
		Assert.assertEquals($set, $unserialized);
	}

	@Test
	public void testIntArray() {
		int[] $array = new int[] {1, 4, 6, 9};
		
		String $serialized = AbusingIO.serialize($array);
		Assert.assertNotNull($serialized);
		
		Object $unserialized = AbusingIO.unserialize($serialized);
		Assert.assertNotNull($unserialized);
		Assert.assertTrue($unserialized instanceof int[]);
		Assert.assertArrayEquals($array, (int[]) $unserialized);
	}
}
