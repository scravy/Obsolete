package net.scravy.weblet.forms;

import java.math.BigInteger;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

class BigIntegerValidator implements Validator<BigInteger> {

	private final FormFieldDefinition formField;
	
	BigIntegerValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}
	
	@Override
	public Either<Okay, String> validate(
			Weblet weblet, Form form, BigInteger value) {

		if (value == null && formField.isRequired()) {
			return Validation.fail(REQUIRED_FIELD_MISSING);
		}
		
		if (formField.getDecimalMax() != null) {
			BigInteger max = new BigInteger(formField.getDecimalMax());
			if (value.compareTo(max) > 0) {
				return Validation.fail(TOO_LARGE);
			}
		}
		if (formField.getDecimalMin() != null) {
			BigInteger min = new BigInteger(formField.getDecimalMin());
			if (value.compareTo(min) < 0) {
				return Validation.fail(TOO_LOW);
			}
		}
		
		return Validation.OK;
	}
	
}