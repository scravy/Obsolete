package net.scravy.persistence;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import net.scravy.technetium.util.IOUtil;
import net.scravy.technetium.util.RuntimeIOException;
import net.scravy.technetium.util.function.Function2;
import net.scravy.technetium.util.value.StringUtil;

import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.tools.schemaframework.SchemaManager;

/**
 * Utility methods for working with a JPA database.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class PersistenceUtil {

	/**
	 * Uses the given EntityManager and creates all tables defined in the domain
	 * package.
	 * <p>
	 * The current implementation of this method depends on EclipseLink.
	 * </p>
	 * 
	 * @since 1.0
	 * @param em
	 *            The EntityManager used for creating the tables.
	 */
	public static void createTables(EntityManager em) {
		DatabaseSession ds = em.unwrap(DatabaseSession.class);
		SchemaManager sm = new SchemaManager(ds);

		sm.createDefaultTables(true);
	}

	/**
	 * Retrieves a listing of all the tables in the database.
	 * <p>
	 * The current implementation of this method is independent of a particular
	 * JPA implementation.
	 * </p>
	 * 
	 * @since 1.0
	 * @param em
	 *            The EntityManager used to discover the database.
	 * @return A List of table names in the selected database.
	 */
	public static List<String> getSchemaTableNames(EntityManager em) {
		Metamodel meta = em.getMetamodel();
		Set<EntityType<?>> entityTypes = meta.getEntities();
		List<String> tableNames = new ArrayList<String>(entityTypes.size());
		for (EntityType<?> entityType : entityTypes) {
			tableNames.add(entityType.getJavaType().getAnnotation(Table.class)
					.name());
		}
		Collections.sort(tableNames);
		return tableNames;
	}

	/**
	 * Retrieves a listing of all java types in the schema.
	 * <p>
	 * The current implementation of this method is independent of a particular
	 * JPA implementation.
	 * </p>
	 * 
	 * @since 1.0
	 * @param em
	 *            The EntityManager used to discover the database.
	 * @return A List of class objects describing the entity POJOs.
	 */
	public static List<Class<?>> getSchemaClasses(EntityManager em) {
		Metamodel meta = em.getMetamodel();
		Set<EntityType<?>> entityTypes = meta.getEntities();
		List<Class<?>> entityClasses = new ArrayList<Class<?>>(
				entityTypes.size());
		for (EntityType<?> entityType : entityTypes) {
			entityClasses.add(entityType.getJavaType());
			entityType.getDeclaredSingularAttributes();
		}
		Collections.sort(entityClasses, new Comparator<Class<?>>() {
			@Override
			public int compare(Class<?> left, Class<?> right) {
				return left.getCanonicalName().compareTo(
						right.getCanonicalName());
			}
		});
		return entityClasses;
	}

	/**
	 * Initializes the database with data from CSV files.
	 * <p>
	 * This method seeks for a CSV file with the simple name of the class which
	 * represents an entity and the file name extension “.csv”. The ClassLoader
	 * used is given do discover the csv-files.
	 * </p>
	 * <p>
	 * E.g. for the entity class "org.example.Entity" a file "Entity.csv" is
	 * used for creating a set of rows for "Entity" in the database.
	 * </p>
	 * <p>
	 * The current implementation of this method is independent of a particular
	 * JPA implementation.
	 * </p>
	 * 
	 * @since 1.0
	 * @param em
	 *            The EntityManager used to discover the database.
	 * @param loader
	 *            The ClassLoader used to discover resources (the csv files).
	 */
	public static void initData(EntityManager em, ClassLoader loader) {
		EntityTransaction transaction = em.getTransaction();
		boolean commitTransaction = false;
		if (!transaction.isActive()) {
			transaction.begin();
			commitTransaction = true;
		}
		for (Class<?> clazz : getSchemaClasses(em)) {
			String fileName = clazz.getSimpleName() + ".csv";
			InputStream input = loader.getResourceAsStream(fileName);
			if (input != null) {
				initData(clazz, input, em);
			}
		}
		if (commitTransaction) {
			transaction.commit();
		}
	}

	/**
	 * Initializes the database with data from CSV files.
	 * <p>
	 * This method seeks for a CSV file with the simple name of the class which
	 * represents an entity and the file name extension “.csv”.
	 * </p>
	 * <p>
	 * E.g. for the entity class "org.example.Entity" a file "Entity.csv" is
	 * used for creating a set of rows for "Entity" in the database.
	 * </p>
	 * <p>
	 * The current implementation of this method is independent of a particular
	 * JPA implementation.
	 * </p>
	 * 
	 * @since 1.0
	 * @param em
	 *            The EntityManager used to discover the database.
	 */
	public static void initData(EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
		boolean commitTransaction = false;
		if (!transaction.isActive()) {
			transaction.begin();
			commitTransaction = true;
		}
		for (Class<?> clazz : getSchemaClasses(em)) {
			String fileName = clazz.getSimpleName() + ".csv";
			InputStream input = clazz.getResourceAsStream(fileName);
			if (input != null) {
				initData(clazz, input, em);
			}
		}
		if (commitTransaction) {
			transaction.commit();
		}
	}

	private static <T> Function2<Object, String, Void> converterFunction(
			final Method m, Class<T> returnType) {
		Function2<Object, String, Void> f;

		if (returnType.equals(String.class)) {
			f = new Function2<Object, String, Void>() {
				@Override
				public Void apply(Object obj, String string) {
					try {
						m.invoke(obj, string);
					} catch (Exception exc) {
						System.out.println("WARNING: Exception: "
								+ exc.getMessage());
					}
					return null;
				}
			};
		} else if (returnType.equals(int.class)
				|| returnType.equals(Integer.class)) {
			f = new Function2<Object, String, Void>() {
				@Override
				public Void apply(Object obj, String argument) {
					Object value = Integer.valueOf(argument);
					try {
						m.invoke(obj, value);
					} catch (Exception exc) {
						System.out.println("WARNING: Exception: "
								+ exc.getMessage());
					}
					return null;
				}
			};
		} else if (returnType.equals(long.class)
				|| returnType.equals(Long.class)) {
			f = new Function2<Object, String, Void>() {
				@Override
				public Void apply(Object obj, String argument) {
					Object value = Long.valueOf(argument);
					try {
						m.invoke(obj, value);
					} catch (Exception exc) {
						System.out.println("WARNING: Exception: "
								+ exc.getMessage());
					}
					return null;
				}
			};
		} else if (returnType.equals(char.class)
				|| returnType.equals(Character.class)) {
			f = new Function2<Object, String, Void>() {
				@Override
				public Void apply(Object obj, String argument) {
					Object value = Long.valueOf(argument);
					try {
						m.invoke(obj, value);
					} catch (Exception exc) {
						System.out.println("WARNING: Exception: "
								+ exc.getMessage());
					}
					return null;
				}
			};
		} else {
			f = new Function2<Object, String, Void>() {
				@Override
				public Void apply(Object obj, String argument) {
					return null;
				}
			};
		}

		return f;
	}

	private static void initData(Class<?> clazz, InputStream input,
			EntityManager em) {
		try {
			List<List<String>> data = IOUtil.loadCSV(input);
			if (data.size() > 1 && data.get(0).size() > 0) {
				List<Function2<Object, String, Void>> methods = new ArrayList<Function2<Object, String, Void>>(
						data.get(0).size());

				for (String field : data.get(0)) {
					String getterName = "get" + StringUtil.ucfirst(field);
					String setterName = "set" + StringUtil.ucfirst(field);
					try {
						Method getter = clazz.getMethod(getterName);
						Class<?> type = getter.getReturnType();
						Method setter = clazz.getMethod(setterName, type);

						if ((setter.getModifiers() & Modifier.PUBLIC) != 0) {
							methods.add(converterFunction(setter, type));
						}
					} catch (NoSuchMethodException exc) {
						System.out.println("WARNING: "
								+ exc.getClass().getSimpleName() + ": "
								+ exc.getMessage());
					}
				}
				data.remove(0);
				try {
					for (List<String> line : data) {
						int i = 0;
						if (line.size() >= methods.size()) {
							Object o = clazz.newInstance();
							for (String col : line) {
								methods.get(i).apply(o, col);
								i++;
								if (i >= methods.size()) {
									break;
								}
							}
							em.persist(o);
						}
					}
				} catch (Exception exc) {
					System.out.println("WARNING: newInstance()-Exception: "
							+ exc.getMessage());
				}
			}
		} catch (RuntimeIOException exc) {
			throw new RuntimeException(
					"IOExcpetion happened while reading CSV data.", exc);
		}
	}
}
