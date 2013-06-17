package net.scravy.weblet.forms;

import java.util.Map.Entry;
import java.util.TimeZone;

/**
 * A submitted and preprocessor form (that is: string values are converted, not
 * necessarily validated yet).
 * 
 * Access to forms is provided within a {@link Validator} and a
 * {@link FormHandler}. A Form is something like a DTO (data transfer object,
 * value object) from the user to the database, where the act of writing to the
 * database happens within a {@link FormHandler}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public interface Form extends Iterable<Entry<String, Object>> {

	/**
	 * Retrieve the value associated with the specified key as instance of the
	 * specified type or null, if there is no such value or the value can not be
	 * converted to the specified type.
	 * 
	 * @param key
	 *            The key.
	 * @param type
	 *            The type.
	 * @return The value.
	 * @since 1.0
	 */
	<T> T get(String key, Class<T> type);

	/**
	 * Retrieve the value associated with the specified key as a String or null,
	 * if there is no value associated with the key.
	 * 
	 * @param key
	 *            The key.
	 * @return The value as a String.
	 * @since 1.0
	 */
	String getString(String key);

	/**
	 * Retrieve the raw value associated with the specified key. This is the
	 * user supplied value before conversion.
	 * 
	 * @param key
	 *            The key.
	 * @return The raw, user-supplied value.
	 * @since 1.0
	 */
	String getRaw(String key);

	/**
	 * Retrieve the value associated with the specified key or null, if there is
	 * no such value.
	 * 
	 * @param key
	 *            The key.
	 * @return The value.
	 * @since 1.0
	 */
	Object get(String key);

	/**
	 * Unwraps an instance of the specified class from this Form object.
	 * 
	 * @param formClass
	 *            The class.
	 * @return This as an instance of the specified class or null, if this is
	 *         not of the specified class.
	 * @since 1.0
	 */
	<T extends Form> T unwrap(Class<T> formClass);

	/**
	 * The TimeZone of this form – necessary for performing locale sensitive
	 * conversions of date and time values.
	 * 
	 * @return The TimeZone of this form.
	 * @since 1.0
	 */
	TimeZone getTimeZone();
}