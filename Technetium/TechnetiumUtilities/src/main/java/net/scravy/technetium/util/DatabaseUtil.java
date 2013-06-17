package net.scravy.technetium.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.ValueUtil;

/**
 * Utility methods to ease using Database from within Java. This class solely
 * uses JDBC from the package {@link java.sql} to accomplish what its
 * functionality.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class DatabaseUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	DatabaseUtil() {
	}

	/**
	 * Connect to a database.
	 * 
	 * @param driver
	 *            The class name of a driver to be used.
	 * @param url
	 *            A JDBC connection url to the database.
	 * @param user
	 *            The name of the user used to establish the connection.
	 * @param password
	 *            The password used to access the database with the given
	 *            username.
	 * @return Either a Connection or an Exception explaining the error that
	 *         occured.
	 */
	public static Either<Connection, ? extends Exception> connect(
			String driver, String url, String user, String password) {

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException exc) {
			return ValueUtil.right(exc);
		}

		try {
			Connection c = DriverManager.getConnection(url, user, password);
			c.isValid(0);
			return ValueUtil.left(c);
		} catch (SQLException exc) {
			return ValueUtil.right(exc);
		}
	}

	/**
	 * Connect to a database with a given timeout.
	 * 
	 * @param driver
	 *            The class name of a driver to be used.
	 * @param url
	 *            A JDBC connection url to the database.
	 * @param user
	 *            The name of the user used to establish the connection.
	 * @param password
	 *            The password used to access the database with the given
	 *            username.
	 * @param timeout
	 *            A timeout (in milliseconds). Zero means “no timeout”.
	 * @throws IllegalArgumentException
	 *             if timeout is less than 0.
	 * @return Either a Connection or an Exception explaining the error that
	 *         occured.
	 */
	public static Either<Connection, ? extends Exception> connect(
			String driver, String url, String user, String password, int timeout) {

		if (timeout < 0) {
			throw new IllegalArgumentException(
					"`timeout` must be greater or equal zero.");
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException exc) {
			return ValueUtil.right(exc);
		}

		try {
			Connection c = DriverManager.getConnection(url, user, password);
			c.isValid(timeout);
			return ValueUtil.left(c);
		} catch (SQLException exc) {
			return ValueUtil.right(exc);
		}
	}

	/**
	 * Connect to a database.
	 * 
	 * @param driver
	 *            The class name of the driver to be used.
	 * @param url
	 *            The JDBC-Connection-URL used to connect to the database. This
	 *            may contain user tokens and authentication credentials.
	 * @return Either a Connection or an Exception explaining the issue.
	 * @since 1.0
	 */
	public static Either<Connection, ? extends Exception> connect(
			String driver, String url) {

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException exc) {
			return ValueUtil.right(exc);
		}

		try {
			Connection c = DriverManager.getConnection(url);
			c.isValid(0);
			return ValueUtil.left(c);
		} catch (SQLException exc) {
			return ValueUtil.right(exc);
		}
	}

	/**
	 * Connect to a database with a given timeout.
	 * 
	 * @param driver
	 *            The class name of the driver to be used.
	 * @param url
	 *            The JDBC-Connection-URL used to connect to the database. This
	 *            may contain user tokens and authentication credentials.
	 * @param timeout
	 *            The timeout.
	 * @return Either a Connection or an Exception explaining the issue.
	 * @since 1.0
	 */
	public static Either<Connection, ? extends Exception> connect(
			String driver, String url, int timeout) {

		if (timeout < 0) {
			throw new IllegalArgumentException(
					"`timeout` must be greater or equal zero.");
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException exc) {
			return ValueUtil.right(exc);
		}

		try {
			Connection c = DriverManager.getConnection(url);
			c.isValid(timeout);
			return ValueUtil.left(c);
		} catch (SQLException exc) {
			return ValueUtil.right(exc);
		}
	}

	/**
	 * Retrieve the names of the tables accessible through the current
	 * connection. Like <code>SHOW TABLES</code> in MySQL.
	 * 
	 * @param connection
	 *            The connection.
	 * @return A list of table names.
	 * @throws SQLException
	 *             If an error occurs while accessing the database.
	 * @since 1.0
	 */
	public static List<String> getTableNames(Connection connection)
			throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();

		ResultSet result = metaData.getTables(null, null, "", null);
		List<String> tables = new ArrayList<String>();
		while (result.next()) {
			tables.add(result.getString("TABLE_NAME"));
		}
		Collections.sort(tables);
		return tables;
	}

	/**
	 * Retrieve the names of the tables in the specified schema (database).
	 * 
	 * @param connection
	 *            The connection.
	 * @param schema
	 *            The name of the schema (or database, depending on your
	 *            database system).
	 * @return A list of table names.
	 * @throws SQLException
	 *             If an error occurs while accessing the database.
	 * @since 1.0
	 */
	public static List<String> getTableNames(Connection connection,
			String schema) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();

		ResultSet result = metaData.getTables(null, schema, "", null);
		List<String> tables = new ArrayList<String>();
		while (result.next()) {
			tables.add(result.getString("TABLE_NAME"));
		}
		Collections.sort(tables);
		return tables;
	}

	/**
	 * Drop all datables which are accessible through the current connection.
	 * <p>
	 * Tables are dropped using the
	 * <code>DROP TABLE IF EXISTS ... CASCADE</code> SQL-Statement. For MySQL,
	 * which does not support that kind of statement, another strategy is used –
	 * foreign_key_checks are turned off while dropping tables, so that foreign
	 * key constraints can’t fail.
	 * </p>
	 * 
	 * @param connection
	 *            The connection.
	 * @since 1.0
	 */
	public static void dropTables(Connection connection) {
		try {
			String dbName = connection.getMetaData().getDatabaseProductName()
					.toLowerCase();
			if (dbName.equals("mysql")) {
				connection.createStatement().execute(
						"SET foreign_key_checks = 0;");

				List<String> tables = getTableNames(connection);
				for (String table : tables) {
					connection.createStatement().execute(
							"DROP TABLE `" + table + "`");
				}
				connection.createStatement().execute(
						"SET foreign_key_checks = 1;");
			} else {
				List<String> tables = getTableNames(connection);
				for (String table : tables) {
					connection.createStatement().execute(
							"DROP TABLE IF EXISTS \"" + table + "\" CASCADE");
				}
			}
		} catch (SQLException exc) {

		}
	}
}
