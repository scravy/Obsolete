package net.scravy.weblet.forms;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

class GenericValidator implements Validator<Object> {

	private final FormFieldDefinition formField;
	
	GenericValidator(FormFieldDefinition formFieldDefinition) {
		this.formField = formFieldDefinition;
	}

	@Override
	public Either<Okay, String> validate(Weblet weblet, Form form, Object value) {
		Class<?> type = formField.getJavaType();
		
		if (Number.class.isAssignableFrom(type)) {
			if (type == BigInteger.class) {
				String max = formField.getDecimalMax();
				if (max != null) {
					
				}
			} else if (type == BigDecimal.class) {
				
			} else {
				
			}
		}
		
		return Validation.fail("UNKNOWN_TYPE");
	}

}
