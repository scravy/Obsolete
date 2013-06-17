package net.scravy.weblet.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ConverterClassAdapter
		extends
		XmlAdapter<String, Class<? extends net.scravy.weblet.forms.Converter<?>>> {

	@Override
	public String marshal(
			Class<? extends net.scravy.weblet.forms.Converter<?>> value)
			throws Exception {
		return value.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends net.scravy.weblet.forms.Converter<?>> unmarshal(
			String value) throws Exception {
		Class<?> clazz = Class.forName(value);
		if (net.scravy.weblet.forms.Converter.class.isAssignableFrom(clazz)) {
			return (Class<? extends net.scravy.weblet.forms.Converter<?>>) clazz;
		}
		throw new ClassCastException(
				String.format(
						"Class<%s> can not be cast to Class<? extends net.scravy.weblet.Converter<?,?>>",
						clazz.getCanonicalName()));
	}
}