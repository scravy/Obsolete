package net.scravy.weblet.forms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

class GenericForm implements Form {

	private final Map<String, Object> values = new HashMap<String, Object>();
	private final Map<String, String> rawValues = new HashMap<String, String>();
	private final TimeZone timeZone;

	GenericForm(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	void put(String key, Object value, String raw) {
		values.put(key, value);
		rawValues.put(key, raw);
	}

	@Override
	public Iterator<Entry<String, Object>> iterator() {
		return values.entrySet().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type) {
		return (T) get(key);
	}

	@Override
	public String getString(String key) {
		if (values.containsKey(key)) {
			return values.get(key).toString();
		}
		return "";
	}

	@Override
	public Object get(String key) {
		return values.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Form> T unwrap(Class<T> formClass) {
		if (formClass.isAssignableFrom(getClass())) {
			return (T) this;
		}
		return null;
	}

	@Override
	public String getRaw(String key) {
		return rawValues.get(key);
	}

	@Override
	public TimeZone getTimeZone() {
		return timeZone;
	}

}