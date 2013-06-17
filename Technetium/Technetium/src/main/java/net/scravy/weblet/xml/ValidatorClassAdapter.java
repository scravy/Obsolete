package net.scravy.weblet.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ValidatorClassAdapter
		extends
		XmlAdapter<String, Class<? extends net.scravy.weblet.forms.Validator<?>>> {

	@Override
	public String marshal(
			Class<? extends net.scravy.weblet.forms.Validator<?>> value)
			throws Exception {
		return value.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends net.scravy.weblet.forms.Validator<?>> unmarshal(
			String v)
			throws Exception {
		Class<?> clazz = Class.forName(v);
		if (net.scravy.weblet.forms.Validator.class.isAssignableFrom(clazz)) {
			return (Class<? extends net.scravy.weblet.forms.Validator<?>>) clazz;
		}
		throw new ClassCastException(
				String.format(
						"Class<%s> can not be cast to Class<? extends net.scravy.weblet.Validator<?>>",
						clazz.getCanonicalName()));
	}
}