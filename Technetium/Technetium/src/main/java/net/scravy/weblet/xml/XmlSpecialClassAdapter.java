package net.scravy.weblet.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public abstract class XmlSpecialClassAdapter<T>
		extends XmlAdapter<String, Class<? extends T>> {

	private final Class<T> baseClass;

	protected XmlSpecialClassAdapter(Class<T> baseClass) {
		this.baseClass = baseClass;
	}

	@Override
	public String marshal(
			Class<? extends T> value)
			throws Exception {
		return value.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends T> unmarshal(String v)
			throws Exception {
		Class<?> clazz = Class.forName(v);
		if (baseClass.isAssignableFrom(clazz)) {
			return (Class<? extends T>) clazz;
		}
		throw new ClassCastException(
				String.format(
						"Class<%s> can not be cast to %s",
						clazz.getCanonicalName(), baseClass));
	}
}