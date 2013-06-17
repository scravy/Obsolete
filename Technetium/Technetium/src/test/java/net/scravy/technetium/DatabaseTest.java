package net.scravy.technetium;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import junit.framework.Assert;
import net.scravy.persistence.EntityManager;
import net.scravy.persistence.EntityManagerWrapper;
import net.scravy.persistence.PersistenceUtil;
import net.scravy.technetium.domain.Person;
import net.scravy.technetium.util.BCrypt;
import net.scravy.technetium.util.DatabaseUtil;
import net.scravy.technetium.util.value.Either;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DatabaseTest {

	public static final String url = "jdbc:derby:memory:%s;create=true";
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String persistenceUnit = "technetium";

	private static Object entityManagerFactoryLock = new Object();
	private static EntityManagerFactory entityManagerFactory = null;
	private static ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<EntityManager>();

	@Test
	public void testInMemoryDerbyDB() throws SQLException {
		connect().close();
	}

	public static Connection connect() {
		Either<Connection, ? extends Exception> connection = DatabaseUtil
				.connect(driver, url);
		Assert.assertTrue(connection.isLeft());
		return connection.getLeft();
	}

	public static void init() {
		BasicConfigurator.configure();
		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
		Logger.getLogger("org.jboss").setLevel(Level.WARN);

		System.setProperty("eclipselink.logging.level", "WARNING");

		try {
			connect().close();
		} catch (SQLException exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void initData(EntityManager db) {
		Person p = new Person();

		p.setFirstName("John");
		p.setLastName("Doe");
		p.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
		p.setLoginName("john.doe");
		p.setDisplayName("Johnny");
		p.setEmail("john.doe@example.com");

		try {
			db.persist(p);
		} catch (ConstraintViolationException exc) {
			for (ConstraintViolation<?> cv : exc.getConstraintViolations()) {
				System.err.printf("%s : %s\n",
						cv.getPropertyPath().toString(),
						cv.getInvalidValue());
			}
			throw exc;
		}
	}

	public static EntityManager getEntityManager() {
		synchronized (entityManagerFactoryLock) {
			if (entityManagerFactory == null) {
				Properties p = new Properties();

				p.setProperty("javax.persistence.jdbc.driver", driver);
				p.setProperty("javax.persistence.jdbc.url",
						String.format(url, "database"));

				entityManagerFactory = Persistence.createEntityManagerFactory(
						persistenceUnit, p);

				EntityManager em = EntityManagerWrapper
						.wrap(entityManagerFactory.createEntityManager());
				PersistenceUtil.createTables(em);
				initData(em);
				em.close();
			}
		}
		EntityManager entityManager = threadLocalEntityManager.get();
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = EntityManagerWrapper.wrap(entityManagerFactory
					.createEntityManager());
			threadLocalEntityManager.set(entityManager);
		}

		return entityManager;
	}
}
