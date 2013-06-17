package net.scravy.weblet.forms;

import net.scravy.technetium.util.TypeUtil;
import net.scravy.weblet.Weblet;

/**
 * A GenericConverter does a simply type conversion for a FormField to its type.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
class GenericConverter implements Converter<Object> {

	private final FormFieldDefinition formField;

	GenericConverter(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}

	/**
	 * Delegates the conversion to {@link TypeUtil#convert(String, Class)}.
	 */
	@Override
	public Object convert(Weblet weblet, Form form, String from) {
		return TypeUtil.convert(from, formField.getJavaType());
	}

}
