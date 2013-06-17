package net.scravy.technetium.modules.admin;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Form;
import net.scravy.weblet.forms.Validator;
import net.scravy.weblet.forms.Validator.Validation.Okay;

public class LoginNameValidator implements Validator<String> {

	@Override
	public Either<Okay, String> validate(Weblet weblet, Form form, String value) {
		return Validation.OK;
	}

}
