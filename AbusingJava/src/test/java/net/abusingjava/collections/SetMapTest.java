package net.abusingjava.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetMapTest {

	@Test
	public void testSize() {
		SetMap<String, Integer> $setMap = AbusingCollections.createSetMap();

		$setMap.addAll("test", new Integer[]{1, 2, 3});
		$setMap.add("test", 3);
		$setMap.add("string", -1335);

		assertEquals(2, $setMap.size());
		assertEquals(4, $setMap.getNumberOfValues());
		assertEquals(3, $setMap.getNumberOfValues("test"));
	}

	@Test
	public void testEmptyValues() {
		SetMap<String, Integer> $setMap = AbusingCollections.createSetMap();

		assertNotNull($setMap.valuesForKey("key"));
		assertEquals(0, $setMap.valuesForKey("key").size());
	}

}
