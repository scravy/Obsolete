package net.scravy.weblet;

import java.util.Set;

public interface Request {

	Session getSession();

	/**
	 * 
	 * @return This method will never return null. An array of pieces in the
	 *         path, relative to the context. The array may be empty
	 *         (zero-sized) if the path does not has any components (e.g. "/" or
	 *         "").
	 */
	String[] getPath();

	String getRootPath();

	/**
	 * 
	 * @param header
	 *            The name of the header to retrieve.
	 * @return This method will never return null. If the header is not set in
	 *         the request, it returns the empty string.
	 */
	String getHeader(String header);

	RequestMethod getMethod();

	/**
	 * Return a named parameter.
	 * 
	 * If multiple values are associated with the parameter, the first will be
	 * returned.
	 * 
	 * @param key
	 *            The name of the parameter.
	 * @return This method will never return null. If the parameter is not
	 *         available, it will return the empty string. Use
	 *         {@link #has(String)} to check if a parameter is cotained in a
	 *         request or not.
	 */
	String getString(String key);

	String[] getStrings(String key);

	/**
	 * 
	 * @param key
	 *            The name of the parameter.
	 * @param defaultValue
	 *            The value to return if the parameter does not exist.
	 * @return This method will return null iff defaultValue is null and the
	 *         parameter does not exist. If you don't pass null as defaultValue
	 *         this method will never return null.
	 */
	<T> T get(String key, T defaultValue);

	<T> T[] get(String key, T[] defaultValue);

	Set<String> getKeys();

	/**
	 * 
	 * @return This method will never return null. If the url did not contain a
	 *         query string, this method returns the empty string ("/url" and
	 *         "/url?" will both result in "").
	 */
	String getQueryString();

	boolean isLoggedIn();

	boolean has(String key);
}
