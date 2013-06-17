package net.scravy.weblet.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.scravy.weblet.forms.FormValidator;

@XmlRootElement(name = "form")
public class FormXml {

	private String name;

	private List<FormFieldInfoXml> fieldInfo = new ArrayList<FormFieldInfoXml>();

	private List<String> order = new ArrayList<String>();

	private Class<? extends FormValidator> validator;

	@XmlAttribute(required = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}

	@XmlElements(value = {
			@XmlElement(name = "field", type = FormFieldXml.class),
			@XmlElement(name = "fields", type = FormFieldsXml.class) })
	public List<FormFieldInfoXml> getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(List<FormFieldInfoXml> fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	@XmlAttribute(required = false)
	@XmlJavaTypeAdapter(value = FormValidatorClassAdapter.class)
	public Class<? extends FormValidator> getValidator() {
		return validator;
	}

	public void setValidator(Class<? extends FormValidator> validator) {
		this.validator = validator;
	}
}