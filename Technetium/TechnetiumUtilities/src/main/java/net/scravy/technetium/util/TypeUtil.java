package net.scravy.technetium.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility methods for handling types and type conversions without pain.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class TypeUtil {

	/**
	 * Forbid instantiation of this class. The constructor is package-private so
	 * no warnings about an unused constructor will show up.
	 */
	TypeUtil() {
	}

	/**
	 * Converts a string to the given class.
	 * 
	 * Typed covered are: String, Boolean, Integer, Long, Double, Float, Short,
	 * Byte, Character, BigInteger, BigDecimal.
	 * 
	 * Conversion rules for Booleans: A string value of "true" or "yes" is
	 * considered true (case insensitive), everything else is considered "false"
	 * (such as the empty string).
	 * 
	 * Conversion rules for “null” and Void: This typed will yield null for
	 * every input string.
	 * 
	 * An input string of null will return null for every type.
	 * 
	 * @param string
	 *            The string which is to be converted.
	 * @param type
	 *            The destined type.
	 * @return The converted value or null if the value could not be converted.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(String string, Class<T> type) {
		if (type == null || type == Void.class) {
			return null;
		}
		if (string == null) {
			return null;
		}
		if (type == String.class) {
			return (T) string;
		}
		if (type == Boolean.class) {
			if ("true".equalsIgnoreCase(string)
					|| "yes".equalsIgnoreCase(string)) {
				return (T) Boolean.TRUE;
			}
			return (T) Boolean.FALSE;
		}
		if (Number.class.isAssignableFrom(type)) {
			try {
				if (type == Integer.class) {
					return (T) Integer.valueOf(string);
				} else if (type == Long.class) {
					return (T) Long.valueOf(string);
				} else if (type == Double.class) {
					return (T) Double.valueOf(string);
				} else if (type == Float.class) {
					return (T) Float.valueOf(string);
				} else if (type == BigInteger.class) {
					return (T) new BigInteger(string);
				} else if (type == BigDecimal.class) {
					return (T) new BigDecimal(string);
				} else if (type == Short.class) {
					return (T) Float.valueOf(string);
				} else if (type == Byte.class) {
					return (T) Float.valueOf(string);
				}
			} catch (Exception exc) {
				return null;
			}
		}
		if (type == Character.class) {
			if (string.length() == 1) {
				return (T) Character.valueOf(string.charAt(0));
			}
		}
		return null;
	}

	/**
	 * Converts a string to the type of the given defaultValue or returns that
	 * defaultValue if a conversion is not possible.
	 * 
	 * Typed covered are: String, Boolean, Integer, Long, Double, Float, Short,
	 * Byte, Character, BigInteger, BigDecimal.
	 * 
	 * Conversion rules for Booleans: A string value of "true" or "yes" is
	 * considered true (case insensitive), a string value of "false" or "no" is
	 * considered false (also case insensitive). Everything else will yield the
	 * default value.
	 * 
	 * An input string of null will return the defaultValue for every type.
	 * 
	 * @param string
	 *            The string which is to be converted.
	 * @param defaultValue
	 *            The defaultValue to be used if a conversion could not be made.
	 *            This argument also marks the result type.
	 * @return The converted value or the default value if the value could not
	 *         be converted. null is returned only iff string and the
	 *         defaultValue are null.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(String string, T defaultValue) {
		if (string == null) {
			return defaultValue;
		}
		if (defaultValue instanceof String) {
			return (T) string;
		}
		if (defaultValue instanceof Boolean) {
			if ("true".equalsIgnoreCase(string)
					|| "yes".equalsIgnoreCase(string)) {
				return (T) Boolean.TRUE;
			} else if ("false".equalsIgnoreCase(string)
					|| "no".equalsIgnoreCase(string)) {
				return (T) Boolean.FALSE;
			}
		}
		if (defaultValue instanceof Number) {
			try {
				if (defaultValue instanceof Integer) {
					return (T) Integer.valueOf(string);
				} else if (defaultValue instanceof Long) {
					return (T) Long.valueOf(string);
				} else if (defaultValue instanceof Double) {
					return (T) Double.valueOf(string);
				} else if (defaultValue instanceof Float) {
					return (T) Float.valueOf(string);
				} else if (defaultValue instanceof BigInteger) {
					return (T) new BigInteger(string);
				} else if (defaultValue instanceof BigDecimal) {
					return (T) new BigDecimal(string);
				} else if (defaultValue instanceof Short) {
					return (T) Float.valueOf(string);
				} else if (defaultValue instanceof Byte) {
					return (T) Float.valueOf(string);
				}
			} catch (Exception exc) {
				return defaultValue;
			}
		}
		if (defaultValue instanceof Character) {
			if (string.length() == 1) {
				return (T) Character.valueOf(string.charAt(0));
			}
		}
		return defaultValue;
	}

}
