package net.abusingjava;

import static net.abusingjava.AbusingJava.t;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TripleTest {

	@Test
	public void setTest() {
		Set<Triple<String,String,String>> $set = new HashSet<Triple<String,String,String>>();
		
		assertEquals(0, $set.size());
		$set.add(t("Hallo", "Welt", "da"));
		assertEquals(1, $set.size());
		$set.add(t("Hallo", "Welt", "da"));
		assertEquals(1, $set.size());
		$set.add(t("Welt", "Hallo", "da"));
		assertEquals(2, $set.size());
	}
	
}
