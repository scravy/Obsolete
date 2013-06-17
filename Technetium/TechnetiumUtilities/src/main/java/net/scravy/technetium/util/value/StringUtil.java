package net.scravy.technetium.util.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods for working with String values.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class StringUtil {

	/**
	 * Returns a string with its first letter capitalized.
	 * 
	 * @param string
	 *            The String.
	 * @return The same string with the first letter being an upper case
	 *         character.
	 * @since 1.0
	 */
	public static String ucfirst(String string) {
		if (string == null) {
			return null;
		}
		if (string.isEmpty()) {
			return string;
		}
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}

	/**
	 * Groups a list of strings by common prefixes.
	 * 
	 * @param strings
	 *            The strings.
	 * @param delimiter
	 *            The delimiter which separates the prefix form the value (a
	 *            regular expression as for {@link String#split(String)}).
	 * @return A Map of Lists where the keys are the prefixes and the Lists
	 *         contain the values associated with a prefix.
	 */
	public static Map<String, List<String>> groupByPrefix(
			Iterable<String> strings,
			String delimiter) {
		Map<String, List<String>> groups = new HashMap<String, List<String>>();

		for (String string : strings) {
			String[] splitted = string.split(delimiter, 2);
			if (splitted.length == 2) {
				if (!groups.containsKey(splitted[0])) {
					groups.put(splitted[0], new ArrayList<String>());
				}
				groups.get(splitted[0]).add(splitted[1]);
			}
		}

		return groups;
	}

}
