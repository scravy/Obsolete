package net.scravy.weblet.forms;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.scravy.weblet.xml.FormFieldXml;
import net.scravy.weblet.xml.XmlPatternAdapter;

public class FormFieldDefinition {

	/**
	 * The name of this form field.
	 */
	private final String name;

	/**
	 * The definition of the form containing this form field.
	 */
	private final FormDefinition parent;

	/**
	 * The type of this field.
	 */
	private Class<?> javaType;

	/**
	 * The minimum value, if this is an integral type. Same as {@link Min}.
	 * Defaults to {@link Long#MIN_VALUE}.
	 */
	private long min = Long.MIN_VALUE;

	/**
	 * The maximum value, if this is an integral type. Same as {@link Max}.
	 * Defaults to {@link Long#MAX_VALUE}.
	 */
	private long max = Long.MAX_VALUE;

	/**
	 * The minimum length for a String value. A negative value means “no
	 * minimum”. Defaults to -1.
	 */
	private int minLength = -1;

	/**
	 * The maximum length for a String value. A negative value means “no
	 * maximum”. Defaults to -1.
	 */
	private int maxLength = -1;

	/**
	 * The minimum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMin}. Defaults to null which means no maximum.
	 */
	private String decimalMin = null;

	/**
	 * The maximum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMax}. Defaults to null which means no maximum.
	 */
	private String decimalMax = null;

	/**
	 * A regular expression that this form fields String value must satisfy (or
	 * null if no such constraint is applied). Defaults to null.
	 */
	private java.util.regex.Pattern regex = null;

	/**
	 * Whether this field is required or not. Defaults to false.
	 */
	private boolean required = false;

	private Class<? extends Converter<?>> converterClass = null;

	private Class<? extends Validator<?>> validatorClass = null;

	FormFieldDefinition(FormDefinition parent, String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					"The `name` of a FormField may not be null nor empty.");
		}
		if (parent == null) {
			throw new IllegalArgumentException(
					"The `parent` form of a FormField must not be null.");
		}

		this.parent = parent;
		this.name = name;
	}

	FormFieldDefinition() {
		name = null;
		parent = null;
	}

	/**
	 * Creates a FormFieldDefinition from a {@link FormFieldXml XML Definition
	 * of a FormField}.
	 */
	FormFieldDefinition(FormDefinition parent, FormFieldXml formFieldXml) {

		this.parent = parent;
		this.name = formFieldXml.getName();

		this.javaType = formFieldXml.getJavaType();

		importDeclarations(formFieldXml);
	}

	/**
	 * Creates a FormFieldDefinition from a {@link PropertyDescriptor} which
	 * describes a java bean property - this is done for example when building a
	 * form from a POJO.
	 */
	FormFieldDefinition(FormDefinition parent, PropertyDescriptor property)
			throws FormProcessorException {

		this.parent = parent;
		this.name = property.getName();
		this.javaType = property.getPropertyType();

		importDeclarations(property);
	}

	public void importDeclarations(PropertyDescriptor property)
			throws FormProcessorException {
		try {
			if (property.getReadMethod() != null) {
				for (Annotation annotation : property.getReadMethod()
						.getAnnotations()) {
					importDeclarations(annotation);
				}
			}
			if (property.getWriteMethod() != null) {
				for (Annotation annotation : property.getWriteMethod()
						.getAnnotations()) {
					importDeclarations(annotation);
				}
			}

			Field field = null;
			try {
				field = property.getReadMethod().getDeclaringClass()
						.getDeclaredField(name);
			} catch (NoSuchFieldException exc) {
				// field will be null, case is covered (see below)
			}
			if (field == null) {
				try {
					field = property.getReadMethod().getDeclaringClass()
							.getDeclaredField('_' + name);
				} catch (NoSuchFieldException exc) {
					// field will still be null, case is covered (see below)
				}
			}
			if (field != null) {
				for (Annotation annotation : field.getAnnotations()) {
					importDeclarations(annotation);
				}
			}
		} catch (Exception exc) {
			throw new FormProcessorException(exc);
		}
	}

	public void importDeclarations(FormFieldXml formField) {
		if (formField.getConverter() != null) {
			setConverterClass(formField.getConverter());
		}
		if (formField.getValidator() != null) {
			setValidatorClass(formField.getValidator());
		}
		if (formField.getJavaType() != null) {
			setJavaType(formField.getJavaType());
		}
	}

	public void importDeclarations(Annotation... annotations)
			throws FormProcessorException {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Min) {
				this.min = ((Min) annotation).value();
			} else if (annotation instanceof Max) {
				this.max = ((Max) annotation).value();
			} else if (annotation instanceof NotNull) {
				this.required = true;
			} else if (annotation instanceof Column) {
				this.required = !((Column) annotation).nullable();
			} else if (annotation instanceof Pattern) {
				setRegex(((Pattern) annotation).regexp());
			} else if (annotation instanceof FormField) {
				FormField formField = (FormField) annotation;

				if (formField.javaType() != Void.class) {
					setJavaType(formField.javaType());
				}
				if (formField.converter() != Converter.Id.class) {
					setConverterClass(formField.converter());
				}
				if (formField.validator() != Validator.AcceptAll.class) {
					setValidatorClass(formField.validator());
				}
				if (!formField.decimalMax().isEmpty()) {
					setDecimalMax(formField.decimalMax());
				}
				if (!formField.decimalMin().isEmpty()) {
					setDecimalMin(formField.decimalMin());
				}
				if (formField.max() < Long.MAX_VALUE) {
					setMax(formField.max());
				}
				if (formField.min() > Long.MIN_VALUE) {
					setMin(formField.min());
				}
				if (!formField.regex().isEmpty()) {
					setRegex(formField.regex());
				}
				if (formField.minLength() >= 0) {
					setMinLength(formField.minLength());
				}
				if (formField.maxLength() >= 0) {
					setMaxLength(formField.maxLength());
				}
			}
		}
	}

	/**
	 * The type of this field.
	 * 
	 * @return The Type.
	 */
	@XmlAttribute
	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public void setJavaType(String javaType) {

	}

	/**
	 * The minimum value, if this is an integral type. Same as {@link Min}.
	 * Defaults to {@link Long#MIN_VALUE}.
	 * 
	 * @return The minimum value.
	 */
	@XmlAttribute
	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	/**
	 * The maximum value, if this is an integral type. Same as {@link Max}.
	 * Defaults to {@link Long#MAX_VALUE}.
	 * 
	 * @return The maximum value.
	 */
	@XmlAttribute
	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	/**
	 * A regular expression that this form fields String value must satisfy (or
	 * null of no such constraint is applied). Defaults to null.
	 * 
	 * @return The Pattern.
	 */
	@XmlJavaTypeAdapter(value = XmlPatternAdapter.class)
	@XmlAttribute
	public java.util.regex.Pattern getRegex() {
		return regex;
	}

	public void setRegex(java.util.regex.Pattern regex) {
		this.regex = regex;
	}

	public void setRegex(String regex) {
		try {
			this.regex = java.util.regex.Pattern.compile(regex);
		} catch (PatternSyntaxException exc) {
			throw new IllegalArgumentException(
					"The given `regex` is not a valid pattern (illegal syntax).",
					exc);
		}
	}

	/**
	 * Whether this field is required or not. Defaults to false.
	 * 
	 * @return True IFF the field is required.
	 */
	@XmlAttribute
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * The name of this form field.
	 * 
	 * @return The Name.
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * The minimum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMin}. Defaults to null which means no maximum.
	 * 
	 * @return The minimum decimal value.
	 */
	@XmlAttribute
	public String getDecimalMin() {
		return decimalMin;
	}

	public void setDecimalMin(String decimalMin) {
		this.decimalMin = decimalMin;
	}

	/**
	 * The maximum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMax}. Defaults to null which means no maximum.
	 * 
	 * @return The maximum decimal value.
	 */
	@XmlAttribute
	public String getDecimalMax() {
		return decimalMax;
	}

	public void setDecimalMax(String decimalMax) {
		this.decimalMax = decimalMax;
	}

	/**
	 * The definition of the form containing this form field.
	 * 
	 * @return The Form containing this FormField.
	 */
	@XmlTransient
	public FormDefinition getParent() {
		return parent;
	}

	@XmlAttribute
	public Class<? extends Converter<?>> getConverterClass() {
		return converterClass;
	}

	public void setConverterClass(Class<? extends Converter<?>> converterClass) {
		this.converterClass = converterClass;
	}

	@XmlTransient
	public Converter<?> getConverter() {
		try {
			if (converterClass == null) {
				return new GenericConverter(this);
			}
			return converterClass.newInstance();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	@XmlAttribute
	public Class<? extends Validator<?>> getValidatorClass() {
		return validatorClass;
	}

	public void setValidatorClass(Class<? extends Validator<?>> validatorClass) {
		this.validatorClass = validatorClass;
	}

	@XmlTransient
	public Validator<?> getValidator() {
		try {
			if (validatorClass == null) {
				if (String.class.equals(javaType)) {
					return new StringValidator(this);
				} else if (Number.class.isAssignableFrom(javaType)) {
					return new NumberValidator(this);
				} else if (Date.class.isAssignableFrom(javaType)) {
					return new DateValidator(this);
				} else if (BigInteger.class.equals(javaType)) {
					return new BigIntegerValidator(this);
				} else if (BigDecimal.class.equals(javaType)) {
					return new BigDecimalValidator(this);
				}
				return Validator.ACCEPT_ALL;
			}
			return validatorClass.newInstance();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	/**
	 * The minimum length for a String value. A negative value means “no
	 * minimum”.
	 * 
	 * @return The minimum length.
	 */
	@XmlAttribute
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	/**
	 * The maximum length for a String value. A negative value means “no
	 * maximum”.
	 * 
	 * @return The maximum length.
	 */
	@XmlAttribute
	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}