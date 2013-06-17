package net.scravy.weblet.forms;

import java.math.BigDecimal;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

class BigDecimalValidator implements Validator<BigDecimal> {

	private final FormFieldDefinition formField;
	
	BigDecimalValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}
	
	@Override
	public Either<Okay, String> validate(
			Weblet weblet, Form form, BigDecimal value) {
		
		if (value == null && formField.isRequired()) {
			return Validation.fail(REQUIRED_FIELD_MISSING);
		}
		
		if (formField.getDecimalMax() != null) {
			BigDecimal max = new BigDecimal(formField.getDecimalMax());
			if (value.compareTo(max) > 0) {
				return Validation.fail(TOO_LARGE);
			}
		}
		if (formField.getDecimalMin() != null) {
			BigDecimal min = new BigDecimal(formField.getDecimalMin());
			if (value.compareTo(min) < 0) {
				return Validation.fail(TOO_LOW);
			}
		}
		
		return Validation.OK;
	}

}