package net.scravy.weblet.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FormValidatorClassAdapter
		extends
		XmlAdapter<String, Class<? extends net.scravy.weblet.forms.FormValidator>> {

	@Override
	public String marshal(
			Class<? extends net.scravy.weblet.forms.FormValidator> value)
			throws Exception {
		return value.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends net.scravy.weblet.forms.FormValidator> unmarshal(
			String v)
			throws Exception {
		Class<?> clazz = Class.forName(v);
		if (net.scravy.weblet.forms.FormValidator.class.isAssignableFrom(clazz)) {
			return (Class<? extends net.scravy.weblet.forms.FormValidator>) clazz;
		}
		throw new ClassCastException(
				String.format(
						"Class<%s> can not be cast to Class<? extends net.scravy.weblet.FormValidator>",
						clazz.getCanonicalName()));
	}
}