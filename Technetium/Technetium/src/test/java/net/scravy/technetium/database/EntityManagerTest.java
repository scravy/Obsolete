package net.scravy.technetium.database;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.scravy.persistence.EntityManager;
import net.scravy.technetium.DatabaseTest;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntityManagerTest {

	@BeforeClass
	public static void before() {
		DatabaseTest.init();
	}

	@Test
	public void testMapQuery() {
		EntityManager em = DatabaseTest.getEntityManager();

		List<Map<String, Object>> result = em.mapQuery(
				"SELECT count(p) as c FROM Person p", 0, 10);
		for (Object o : result) {
			Class<?> c = o.getClass();
			while (c != null) {
				System.out.println(c);
				for (Class<?> i : c.getInterfaces()) {
					System.out.printf("-> %s\n", i);
				}
				c = c.getSuperclass();
			}
			if (o instanceof Map) {
				Map<?, ?> m = (Map<?, ?>) o;
				for (Entry<?, ?> e : m.entrySet()) {
					System.out.printf("%s: %s\n", e.getKey(), e.getValue());
				}
			}
		}
	}

}
