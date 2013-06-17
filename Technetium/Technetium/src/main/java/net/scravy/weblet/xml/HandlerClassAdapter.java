package net.scravy.weblet.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HandlerClassAdapter extends
		XmlAdapter<String, Class<? extends net.scravy.weblet.Handler>> {

	@Override
	public String marshal(Class<? extends net.scravy.weblet.Handler> value)
			throws Exception {
		return value.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends net.scravy.weblet.Handler> unmarshal(String v)
			throws Exception {
		Class<?> clazz = Class.forName(v);
		if (net.scravy.weblet.Handler.class.isAssignableFrom(clazz)) {
			return (Class<? extends net.scravy.weblet.Handler>) clazz;
		}
		throw new ClassCastException(
				String.format(
						"Class<%s> can not be cast to Class<? extends net.scravy.weblet.Handler>",
						clazz.getCanonicalName()));
	}
}