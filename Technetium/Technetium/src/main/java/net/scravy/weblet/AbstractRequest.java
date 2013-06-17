package net.scravy.weblet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.scravy.technetium.util.TypeUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * A base class for request objects, providing some default implementations for
 * many methods.
 * 
 * Subclasses neeed to implement {@link #getSession()}, {@link #getPath()},
 * {@link #getRootPath()}, {@link #getHeader(String)}, and {@link #getMethod()}.
 * 
 * @author Julian Fleischer
 * @version 1.0
 */
public abstract class AbstractRequest implements Request {

	final protected ListMultimap<String, String> parameters;
	final protected String queryString;

	protected AbstractRequest(String queryString) {
		queryString = queryString == null ? "" : queryString;

		this.parameters = ArrayListMultimap.create();
		this.queryString = queryString;

		try {
			String[] params = queryString.split("&");
			for (String param : params) {
				int indexOfDelimiter = param.indexOf('=');
				if (indexOfDelimiter >= 0) {
					String[] pieces = { param.substring(0, indexOfDelimiter),
							param.substring(indexOfDelimiter + 1) };
					pieces[1] = URLDecoder.decode(pieces[1], "UTF-8");
					parameters.put(pieces[0], pieces[1]);
				}
			}
		} catch (UnsupportedEncodingException exc) {
			throw new RuntimeException(exc);
		}
	}

	protected AbstractRequest(String queryString, Map<?, ?> map) {
		this.parameters = ArrayListMultimap.create();
		this.queryString = queryString == null ? "" : queryString;

		for (Entry<?, ?> entry : map.entrySet()) {
			Object value = entry.getValue();
			String key = entry.getKey().toString();
			if (value instanceof String[]) {
				for (String v : (String[]) value) {
					parameters.put(key, v == null ? "" : v);
				}
			} else if (value instanceof Object[]) {
				for (Object v : (Object[]) value) {
					parameters.put(key, v == null ? "" : v.toString());
				}
			} else if (value instanceof Iterable) {
				for (Object v : (Iterable<?>) value) {
					parameters.put(key, v == null ? "" : v.toString());
				}
			} else if (value == null) {
				parameters.put(key, "");
			} else {
				parameters.put(key, value.toString());
			}
		}
	}

	protected AbstractRequest(String queryString,
			ListMultimap<String, String> values) {
		this.parameters = values;
		this.queryString = queryString == null ? "" : queryString;
	}

	@Override
	public String getString(String key) {
		if (parameters.containsKey(key)) {
			return parameters.get(key).get(0);
		}
		return "";
	}

	@Override
	public String[] getStrings(String key) {
		if (parameters.containsKey(key)) {
			List<String> values = parameters.get(key);
			return values.toArray(values.toArray(new String[values.size()]));
		}
		return new String[0];
	}

	@Override
	public <T> T get(String key, T defaultValue) {
		return TypeUtil.convert(getString(key), defaultValue);
	}

	@Override
	public <T> T[] get(String key, T[] defaultValue) {
		return TypeUtil.convert(getString(key), defaultValue);
	}

	@Override
	public Set<String> getKeys() {
		return parameters.keySet();
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public boolean isLoggedIn() {
		return getSession().getUser() != null;
	}

	@Override
	public boolean has(String key) {
		return parameters.containsKey(key);
	}

	@Override
	public abstract Session getSession();

	@Override
	public abstract String[] getPath();

	@Override
	public abstract String getRootPath();

	@Override
	public abstract String getHeader(String header);

	@Override
	public abstract RequestMethod getMethod();

}
