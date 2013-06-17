package net.scravy.technetium.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * An unmodifiable view on an existing properties object.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class UnmodifiableProperties extends EnhancedProperties {

	private static final long serialVersionUID = 3816404152279291002L;
	private final Properties properties;

	/**
	 * Create the view form an existing properties object.
	 * 
	 * @param properties
	 *            The existing properties object.
	 */
	public UnmodifiableProperties(Properties properties) {
		if (properties == null) {
			throw new IllegalArgumentException("`properties` must not be null.");
		}
		this.properties = properties;
	}

	@Override
	public String getProperty(String arg0) {
		return properties.getProperty(arg0);
	}

	@Override
	public String getProperty(String arg0, String arg1) {
		return properties.getProperty(arg0, arg1);
	}

	@Override
	public void list(PrintStream arg0) {
		properties.list(arg0);
	}

	@Override
	public void list(PrintWriter arg0) {
		properties.list(arg0);
	}

	@Override
	public synchronized void load(Reader arg0) throws IOException {
		properties.load(arg0);
	}

	@Override
	public synchronized void load(InputStream arg0) throws IOException {
		properties.load(arg0);
	}

	@Override
	public synchronized void loadFromXML(InputStream arg0) throws IOException,
			InvalidPropertiesFormatException {
		properties.loadFromXML(arg0);
	}

	@Override
	public Enumeration<?> propertyNames() {
		return properties.propertyNames();
	}

	@Override
	public synchronized void save(OutputStream arg0, String arg1) {
		throw new UnsupportedOperationException(
				"Properties.save(OutputStream,String) is deprecated.");
	}

	@Override
	public synchronized EnhancedProperties setProperty(String arg0, String arg1) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public void store(Writer arg0, String arg1) throws IOException {
		properties.store(arg0, arg1);
	}

	@Override
	public void store(OutputStream arg0, String arg1) throws IOException {
		properties.store(arg0, arg1);
	}

	@Override
	public synchronized void storeToXML(OutputStream arg0, String arg1)
			throws IOException {
		properties.storeToXML(arg0, arg1);
	}

	@Override
	public synchronized void storeToXML(OutputStream arg0, String arg1,
			String arg2) throws IOException {
		properties.storeToXML(arg0, arg1, arg2);
	}

	@Override
	public Set<String> stringPropertyNames() {
		return properties.stringPropertyNames();
	}

	@Override
	public synchronized void clear() {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public synchronized Object clone() {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are not cloneable.");
	}

	@Override
	public synchronized boolean contains(Object arg0) {
		return properties.contains(arg0);
	}

	@Override
	public synchronized boolean containsKey(Object arg0) {
		return properties.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return properties.containsValue(arg0);
	}

	@Override
	public synchronized Enumeration<Object> elements() {
		return properties.elements();
	}

	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return properties.entrySet();
	}

	@Override
	public synchronized boolean equals(Object arg0) {
		return properties.equals(arg0);
	}

	@Override
	public synchronized Object get(Object arg0) {
		return properties.get(arg0);
	}

	@Override
	public synchronized int hashCode() {
		return properties.hashCode();
	}

	@Override
	public synchronized boolean isEmpty() {
		return properties.isEmpty();
	}

	@Override
	public Set<Object> keySet() {
		return properties.keySet();
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return properties.keys();
	}

	@Override
	public synchronized Object put(Object arg0, Object arg1) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public synchronized void putAll(Map<? extends Object, ? extends Object> arg0) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	protected void rehash() {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public synchronized Object remove(Object arg0) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public synchronized int size() {
		return properties.size();
	}

	@Override
	public synchronized String toString() {
		return properties.toString();
	}

	@Override
	public Collection<Object> values() {
		return Collections.unmodifiableCollection(properties.values());
	}

	@Override
	public <T> T get(String key, T defaultValue) {
		return TypeUtil.convert(getProperty(key), defaultValue);
	}

	@Override
	public synchronized EnhancedProperties setProperty(String key, Object value) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

	@Override
	public synchronized EnhancedProperties merge(Properties properties) {
		throw new UnsupportedOperationException(
				"UnmodifieableProperties are unmodifieable.");
	}

}