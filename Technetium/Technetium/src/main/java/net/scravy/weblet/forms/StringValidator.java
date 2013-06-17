package net.scravy.weblet.forms;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

/**
 * A Validator that validated a String against constraints imposed by a
 * {@link FormFieldDefinition}.
 * <p>
 * The checked constraints are:
 * </p>
 * <ul>
 * <li>{@link FormField#maxLength()}</li>
 * <li>{@link FormField#minLength()}</li>
 * <li>{@link FormField#regex()}</li>
 * </ul>
 * </p>
 * <p>
 * The class is package private, as it is used by {@link FormFieldDefinition}
 * and {@link FormProcessor} only for validating declarative constraints.
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
class StringValidator implements Validator<String> {

	private final FormFieldDefinition formField;

	StringValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}

	@Override
	public Either<Okay, String> validate(Weblet weblet, Form form, String value) {

		if (value == null && formField.isRequired()) {
			return Validation.fail(REQUIRED_FIELD_MISSING);
		}

		if (formField.getRegex() != null) {
			if (!formField.getRegex().matcher(value).matches()) {
				return Validation.fail(MALFORMED_VALUE);
			}
		}
		if (formField.getMaxLength() >= 0) {
			if (value.length() > formField.getMaxLength()) {
				return Validation.fail(TOO_LONG);
			}
		}
		if (formField.getMinLength() >= 0) {
			if (value.length() > formField.getMinLength()) {
				return Validation.fail(TOO_SHORT);
			}
		}

		return Validation.OK;
	}

}