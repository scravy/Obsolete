package net.scravy.technetium.util.data;

import junit.framework.Assert;

import org.junit.Test;

/**
 * A Unit test for {@link DataTable}.
 */
public class DataTableTest {

	/**
	 * A test for {@link DataTable#join(DataTable, Object)}.
	 * 
	 * @throws Exception
	 *             Anything may happen (but shouldn't).
	 */
	@Test
	public void joinTest() throws Exception {
		DataTable t1 = new DataTable();
		DataTable t2 = new DataTable();

		Assert.assertEquals(t1, t1);
		Assert.assertEquals(t2, t2);

		t1.add(new AssociativeArray()
				.set("Vorname", "Julian")
				.set("Nachname", "Fleischer")
				.set("Matr", 4711337));
		t1.add(new AssociativeArray()
				.set("Vorname", "Benoit")
				.set("Nachname", "Mandelbrot")
				.set("Matr", 4208157));

		t2.add(new AssociativeArray()
				.set("Matr", 4208157)
				.set("Punkte", 17));

		DataTable t3 = t1.join(t2, "Matr", "Matr");
		DataTable t4 = t2.join(t1, "Matr", "Matr");

		Assert.assertEquals(t3, t4);

		System.out.println(t1.outerJoin(t2, "Matr").sort("Nachname"));
	}
}