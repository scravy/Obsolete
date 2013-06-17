package net.scravy.weblet.forms;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Declare constaints on a bean property which is used as form field.
 * 
 * <p>
 * Implementation note: FormFields are processed within
 * {@link FormFieldDefinition#importDeclarations(java.lang.annotation.Annotation...)}
 * .
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 * 
 * @see Form
 * @see FormFieldDefinition
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface FormField {

	Class<?> javaType() default Void.class;

	Class<? extends Converter<?>> converter() default Converter.Id.class;

	Class<? extends Validator<?>> validator() default Validator.AcceptAll.class;

	/**
	 * The minimum value, if the FormField is of an integral type. Same as
	 * {@link Min}. Defaults to {@link Long#MIN_VALUE}.
	 */
	long min() default Long.MIN_VALUE;

	/**
	 * The maximum value, if the FormField is of an integral type. Same as
	 * {@link Max}. Defaults to {@link Long#MAX_VALUE}.
	 */
	long max() default Long.MAX_VALUE;

	/**
	 * The minimum value, if the FormField is of a numeric type but not an
	 * integral one. Same as {@link DecimalMin}. Defaults to null which means no
	 * maximum.
	 */
	String decimalMin() default "";

	/**
	 * The maximum value, if the FormField is of a numeric type but not an
	 * integral one. Same as {@link DecimalMax}. Defaults to null which means no
	 * maximum.
	 */
	String decimalMax() default "";

	/**
	 * A regular expression that this FormFields String value must satisfy (or
	 * the empty String if no such constraint is applied, which is the default).
	 */
	String regex() default "";

	/**
	 * The minimum length for a String value. A negative value means “no
	 * minimum”. Defaults to -1.
	 */
	int minLength() default -1;

	/**
	 * The maximum length for a String value. A negative value means “no
	 * maximum”. Defaults to -1.
	 */
	int maxLength() default -1;
}