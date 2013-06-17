package net.scravy.technetium.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * An enhanced version of the standard Properties class.
 * 
 * This class adds some type safe getters and setters for properties and stores
 * its valued in alphabetical order (this is particularly useful for humand
 * editable configuration files).
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class EnhancedProperties extends Properties {

	private static final long serialVersionUID = -345635400770722625L;

	/**
	 * Return the property item with the given key. The value is converted to
	 * the requested type, if possible. If not (or it the value does not exist),
	 * the specified defaultValue is returned.
	 * 
	 * @param key
	 *            The key.
	 * @param defaultValue
	 *            The value which is to be used if no value is associated with
	 *            this key or the value could not be converted to the requested
	 *            type (determined by the type of this argument – the type of
	 *            the defaultValue).
	 * @return The value converted to the requested type or the defaultValue if
	 *         no such value exists or if the value could not be converted.
	 */
	public <T> T get(String key, T defaultValue) {
		return (T) TypeUtil.convert(getProperty(key), defaultValue);
	}

	/**
	 * Sets a property item.
	 * 
	 * @param key
	 *            The key of the item to be set.
	 * @param value
	 *            The value which is to be associated with the specified key.
	 *            Only the String representation of the value, using its
	 *            {@link Object#toString()} method, is used.
	 * @return This EnhancedProperties instance, useful for chaining method
	 *         calls (known as “fluent interface”).
	 */
	public synchronized EnhancedProperties setProperty(final String key,
			final Object value) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
		super.setProperty(key, value.toString());
		return this;
	}

	@Override
	public synchronized EnhancedProperties setProperty(final String name,
			final String value) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
		super.setProperty(name, value);
		return this;
	}

	/**
	 * Merges property items from another Properties instance into this.
	 * 
	 * @param properties
	 *            The other Properties instance.
	 * @return This EnhancedProperties instance, useful for chaining method
	 *         calls (known as “fluent interface”).
	 */
	public synchronized EnhancedProperties merge(final Properties properties) {
		for (Entry<Object, Object> entry : properties.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
		return this;
	}

	@Override
	public void store(final OutputStream out, final String comments)
			throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		super.store(bytes, comments);
		String[] lines = bytes.toString().split("\n");
		Arrays.sort(lines);
		for (String line : lines) {
			out.write(line.getBytes());
			out.write('\n');
		}
	}
}
