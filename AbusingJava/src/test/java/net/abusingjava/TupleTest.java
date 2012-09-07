package net.abusingjava;

import static net.abusingjava.AbusingJava.t;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TupleTest {

	@Test
	public void setTest() {
		Set<Tuple<String,String>> $set = new HashSet<Tuple<String,String>>();
		
		assertEquals(0, $set.size());
		$set.add(t("Hallo", "Welt"));
		assertEquals(1, $set.size());
		$set.add(t("Hallo", "Welt"));
		assertEquals(1, $set.size());
		$set.add(t("Welt", "Hallo"));
		assertEquals(2, $set.size());
	}
	
}
