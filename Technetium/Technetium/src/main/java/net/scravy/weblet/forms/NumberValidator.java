package net.scravy.weblet.forms;

import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.IllegalNumber;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

class NumberValidator implements Validator<Number> {

	private final FormFieldDefinition formField;
	
	NumberValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}
	
	@Override
	public Either<Okay, String> validate(Weblet weblet, Form form, Number value) {

		if (value == null && formField.isRequired()) {
			return Validation.fail(REQUIRED_FIELD_MISSING);
		}
		
		if (value instanceof IllegalNumber) {
			return Validation.fail(MALFORMED_VALUE);
		}
		
		if (formField.getMax() < Long.MAX_VALUE) {
			if (value.longValue() > formField.getMax()) {
				return Validation.fail(TOO_LARGE);
			}
		}
		if (formField.getMin() > Long.MIN_VALUE) {
			if (value.longValue() < formField.getMin()) {
				return Validation.fail(TOO_LOW);
			}
		}
		
		return Validation.OK;
	}
}