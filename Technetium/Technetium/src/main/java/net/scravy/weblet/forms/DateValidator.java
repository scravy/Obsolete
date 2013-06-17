package net.scravy.weblet.forms;

import java.util.Date;

import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.IllegalDate;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

class DateValidator implements Validator<Date> {

	private final FormFieldDefinition formField;

	DateValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}

	@Override
	public Either<Okay, String> validate(Weblet weblet, Form form, Date value) {

		if (value == null && formField.isRequired()) {
			return Validation.fail(REQUIRED_FIELD_MISSING);
		}

		if (value instanceof IllegalDate) {
			return Validation.fail(ILLEGAL_VALUE);
		}

		return Validation.OK;
	}
}