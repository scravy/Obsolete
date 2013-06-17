package net.scravy.technetium.modules.showcase;

import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.Handler;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Converter;
import net.scravy.weblet.forms.Form;
import net.scravy.weblet.forms.FormHandler;
import net.scravy.weblet.forms.Validator;
import net.scravy.weblet.forms.Validator.Validation.Okay;

public class SimpleForm implements Handler {

	public static class StringSanitizer implements Converter<String> {

		@Override
		public String convert(Weblet weblet, Form form, String string) {
			return string.trim();
		}
	}

	public static class NameValidator implements Validator<String> {

		@Override
		public Either<Okay, String> validate(Weblet weblet, Form form,
				String value) {
			return value.matches("[A-Za-z]+")
					? Validation.OK
					: Validation.fail("MALFORMED");
		}
	}

	public static class LoginNameValidator implements Validator<String> {

		@Override
		public Either<Okay, String> validate(Weblet weblet, Form form,
				String value) {
			int count = weblet.db()
					.createNamedQuery("Auth#checkLoginName", Integer.class)
					.getSingleResult();

			return count == 0
					? Validation.OK
					: Validation.fail("ALREADY_TAKEN");
		}
	}

	public static class CreateForm implements FormHandler {

		@Override
		public void handle(Weblet servlet, Form form) {

		}

	}

	@Override
	public void handle(Weblet weblet, Request request, Response response)
			throws Exception {

		response.setTemplate("showcase-simpleForm");

	}

}