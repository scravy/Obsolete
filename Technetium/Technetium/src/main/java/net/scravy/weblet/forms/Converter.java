package net.scravy.weblet.forms;

import net.scravy.weblet.Weblet;

/**
 * Converts a String value into a specific data type.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @param <T>
 *            The type to be converted To
 */
public interface Converter<T> {

	/**
	 * A converter which does no conversion at all.
	 * 
	 * @author Julian Fleischer
	 * @since 1.0
	 */
	final class Id implements Converter<String> {

		@Override
		public String convert(Weblet weblet, Form form, String from) {
			return from;
		}
	}

	/**
	 * Convert the given string into the destined value, in the context of a
	 * given weblet (if the conversion is, for example, dependent on a certain
	 * value in the database).
	 * 
	 * @param weblet
	 *            The weblet which gives the context for validation (i.e. for
	 *            providing database access).
	 * @param form
	 *            The Form which provides the context for this conversion (such
	 *            as the TimeZone for date values).
	 * @param from
	 *            The string value which is to be converted from.
	 * @return The converted value or null if the value could not be converted.
	 * @since 1.0
	 */
	T convert(Weblet weblet, Form form, String from);

}
