package net.scravy.weblet.forms;

import net.scravy.technetium.util.value.Either;
import net.scravy.technetium.util.value.ValueUtil;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;

/**
 * Validates an already converted value.
 * 
 * @author Julian Fleischer
 * @since 1.0
 * @param <T>
 *            The type which is validated.
 */
public interface Validator<T> {

	/**
	 * A Message indicating that a required fields is missing.
	 */
	final static String REQUIRED_FIELD_MISSING = "REQUIRED_FIELD_MISSING";

	/**
	 * A Message indicating that a required field is too large (e.g. a number is
	 * greater than the allowed maximum value).
	 */
	final static String TOO_LARGE = "TOO_LARGE";

	/**
	 * A Message indicating that a required field is too low (e.g. a number is
	 * less then allowed minimum value).
	 */
	final static String TOO_LOW = "TOO_LOW";

	/**
	 * A Message indicating that a required field is too long (e.g. a String
	 * exceeds the maximum number of characters).
	 */
	final static String TOO_LONG = "TOO_LONG";

	/**
	 * A Message indicating that a required field is too short (e.g. a Strings
	 * length is less then the minimum number of characters).
	 */
	final static String TOO_SHORT = "TOO_SHORT";

	/**
	 * A Message indicating that a given string representation is malformed and
	 * can not be interpreted.
	 */
	final static String MALFORMED_VALUE = "MALFORMED_VALUE";

	/**
	 * A Message indicating that a given string representation is syntactically
	 * correct but makes no sense (such as an illegal date like 2012-02-31).
	 */
	final static String ILLEGAL_VALUE = "ILLEGAL_VALUE";

	/**
	 * A Validator that acceps all values.
	 * 
	 * @author Julian Fleischer
	 * @since 1.0
	 */
	final class AcceptAll implements Validator<Object> {

		@Override
		public Either<Okay, String> validate(Weblet weblet, Form form,
				Object value) {
			return Validation.OK;
		}
	}

	/**
	 * An instance of a Validator that acceps all values.
	 * 
	 * @author Julian Fleischer
	 * @since 1.0
	 */
	final Validator<Object> ACCEPT_ALL = new AcceptAll();

	final class Validation {
		Validation() {
		}

		public static final class Okay {
			public Okay() {
			}
		}

		public static final Okay OKAY = new Okay();

		public static final Either<Okay, String> OK = ValueUtil
				.left(OKAY);

		public static Either<Okay, String> fail(Object reason) {
			return ValueUtil.right(reason == null
					? "NO_REASON" : reason.toString());
		}
	}

	/**
	 * Validates a value.
	 * 
	 * @param weblet
	 *            The weblet which provides the context for any form
	 *            validations.
	 * @param form
	 *            The form which the validated field is part of. This allows to
	 *            access other values in the form, if the validity of a value
	 *            depends on other values in the form.
	 * @param value
	 *            The actual value to be validated.
	 * 
	 * @return Either Okay or a String explaining the validation error.
	 */
	Either<Okay, String> validate(Weblet weblet, Form form, T value);

}
