package net.scravy.weblet.forms;

import java.util.ArrayList;
import java.util.List;

import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.ValueUtil;
import net.scravy.weblet.Request;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation;
import net.scravy.weblet.forms.Validator.Validation.Okay;
import net.scravy.weblet.xml.FormXml;

/**
 * A FormProcessor processes a {@link Request} according to a given
 * {@link FormXml} definition.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
class FormProcessor {

	private final FormDefinition formDefinition;

	FormProcessor(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	Either<Okay, ValidationReport> process(Weblet weblet, Request request) {
		List<ValidationViolation> violations =
				new ArrayList<ValidationViolation>(
						formDefinition.getFormFields().size());

		GenericForm form = new GenericForm(
				weblet.getTimeZoneForRequest(request));

		for (FormFieldDefinition formField : formDefinition) {
			try {
				String name = formDefinition.getName() + '.'
						+ formField.getName();
				String stringValue = request.getString(name);

				Object value = formField.getConverter().convert(
						weblet, form, stringValue);

				form.put(formField.getName(), value, stringValue);
			} catch (Exception exc) {
				violations.add(new ValidationViolation(
						formField.getName(), exc));
			}
		}

		for (FormFieldDefinition formField : formDefinition) {
			try {
				Object value = form.get(formField.getName());
				@SuppressWarnings("unchecked")
				Validator<Object> validator =
						((Validator<Object>) formField.getValidator());
				Either<Okay, String> result =
						validator.validate(weblet, form, value);
				if (result.isRight()) {
					violations.add(new ValidationViolation(
							formField.getName(), result.getRight()));
				}
			} catch (Exception exc) {
				violations.add(
						new ValidationViolation(formField.getName(), exc));
			}
		}

		if (violations.isEmpty()) {
			return ValueUtil.left(new Validation.Okay());
		}
		return ValueUtil.right(new ValidationReport(violations));
	}

	public FormDefinition getFormDefinition() {
		return this.formDefinition;
	}
}
