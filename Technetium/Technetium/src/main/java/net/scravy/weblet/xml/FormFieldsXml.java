package net.scravy.weblet.xml;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;

@SuppressWarnings("serial")
public class FormFieldsXml implements FormFieldInfoXml, Serializable {

	private Class<?> from;

	private Set<String> include;

	private Set<String> exclude;

	@XmlAttribute(required = true)
	public Class<?> getFrom() {
		return from;
	}

	public void setFrom(Class<?> from) {
		this.from = from;
	}

	@XmlAttribute(required = false)
	public Set<String> getInclude() {
		return include;
	}

	public void setInclude(Set<String> include) {
		this.include = include;
	}

	@XmlAttribute(required = false)
	public Set<String> getExclude() {
		return exclude;
	}

	public void setExclude(Set<String> exclude) {
		this.exclude = exclude;
	}
}