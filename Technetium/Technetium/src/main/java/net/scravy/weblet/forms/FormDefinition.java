package net.scravy.weblet.forms;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.scravy.weblet.xml.FormFieldInfoXml;
import net.scravy.weblet.xml.FormFieldXml;
import net.scravy.weblet.xml.FormFieldsXml;
import net.scravy.weblet.xml.FormXml;

@XmlRootElement
public class FormDefinition implements Iterable<FormFieldDefinition> {

	final private Map<String, FormFieldDefinition> formFields =
			new HashMap<String, FormFieldDefinition>();

	final private String name;
	final private String simpleName;
	final private List<String> order = new ArrayList<String>();

	FormDefinition() {
		name = simpleName = null;
	}

	/**
	 * Builds a FormDefinition from an XML Definition of a form.
	 * 
	 * @param formName
	 *            The complete name of this form, a value of the form
	 *            “Module.simpleName”
	 * @param formXml
	 *            The XML Definition of the form.
	 * @throws FormProcessorException
	 *             If an illegal definition is encountered (such as invalid
	 *             regular expressions etc.)
	 * @throws IllegalArgumentException
	 *             If the given complete name is not valid according to the
	 *             simple name.
	 */
	FormDefinition(String formName, FormXml formXml)
			throws FormProcessorException {
		this.name = formName;
		this.simpleName = formXml.getName();

		order.addAll(formXml.getOrder());

		if (!name.endsWith('.' + simpleName)) {
			throw new IllegalArgumentException(
					String.format(
							"The complete name “%s” is not valid according to "
									+ "the forms simple name “%s”. Its simple "
									+ "name component does not match.",
							name, simpleName));
		}

		for (FormFieldInfoXml fieldInfo : formXml.getFieldInfo()) {
			if (fieldInfo instanceof FormFieldsXml) {
				FormFieldsXml formFields = (FormFieldsXml) fieldInfo;

				Set<String> include = formFields.getInclude();
				Set<String> exclude = formFields.getExclude();

				Class<?> from = formFields.getFrom();

				try {
					BeanInfo beanInfo = Introspector.getBeanInfo(from);

					PropertyDescriptor[] properties =
							beanInfo.getPropertyDescriptors();

					for (PropertyDescriptor property : properties) {
						String name = property.getName();

						if (exclude.contains(name)) {
							continue;
						}
						if (!include.isEmpty() && !include.contains(name)) {
							continue;
						}

						FormFieldDefinition def =
								new FormFieldDefinition(this, property);
						this.formFields.put(name, def);

						if (!order.contains(name)) {
							order.add(name);
						}
					}

				} catch (Exception exc) {
					throw new FormProcessorException(exc);
				}
			}
		}

		for (FormFieldInfoXml fieldInfo : formXml.getFieldInfo()) {
			if (fieldInfo instanceof FormFieldXml) {
				FormFieldXml formField = (FormFieldXml) fieldInfo;

				if (this.formFields.containsKey(formField.getName())) {
					this.formFields.get(
							formField.getName()).importDeclarations(formField);
				} else {
					String name = formField.getName();
					this.formFields.put(
							name, new FormFieldDefinition(this, formField));

					if (!order.contains(name)) {
						order.add(name);
					}
				}
			}
		}
	}

	@XmlTransient
	public Map<String, FormFieldDefinition> getFormFields() {
		return Collections.unmodifiableMap(formFields);
	}

	@XmlElements(value = @XmlElement(type = FormFieldDefinition.class, name = "formField"))
	public List<FormFieldDefinition> getFormFieldsList() {
		List<FormFieldDefinition> list =
				new ArrayList<FormFieldDefinition>(formFields.values());

		Collections.sort(list, new Comparator<FormFieldDefinition>() {
			@Override
			public int compare(FormFieldDefinition left,
					FormFieldDefinition right) {
				return order.indexOf(left.getName())
						- order.indexOf(right.getName());
			}
		});

		return list;
	}

	@XmlAttribute
	public String getSimpleName() {
		return simpleName;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	@Override
	public Iterator<FormFieldDefinition> iterator() {
		return formFields.values().iterator();
	}

}