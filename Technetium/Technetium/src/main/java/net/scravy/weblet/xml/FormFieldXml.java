package net.scravy.weblet.xml;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.scravy.weblet.forms.Converter;
import net.scravy.weblet.forms.Validator;

/**
 * The definition of a FormField, serializable as XML.
 * 
 * <h2>Examples</h2>
 * <p>
 * A simple field with a {@link Validator} and a {@link Converter}.
 * </p>
 * 
 * <pre>
 * &lt;field
 * 		name="userEmail"
 * 		validator="org.example.SampleValidator"
 * 		converter="org.example.SampleConverter" /&gt;
 * </pre>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("serial")
public class FormFieldXml implements FormFieldInfoXml, Serializable {
	/**
	 * The name of this form field.
	 */
	private String name;

	private Class<? extends Validator<?>> validator;

	private Class<? extends Converter<?>> converter;

	private boolean ajaxValidator = false;

	/**
	 * The type of this field.
	 */
	private Class<?> javaType = String.class;

	/**
	 * The minimum value, if this is an integral type. Same as {@link Min}.
	 */
	private Long min;

	/**
	 * The maximum value, if this is an integral type. Same as {@link Max}.
	 */
	private Long max;

	/**
	 * The minimum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMin}. Defaults to null which means no maximum.
	 */
	private String decimalMin;

	/**
	 * The maximum value, if this is a numeric type but not an integral one.
	 * Same as {@link DecimalMax}. Defaults to null which means no maximum.
	 */
	private String decimalMax;

	private int minLength = -1;

	private int maxLength = -1;

	private Pattern regex;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(required = false)
	@XmlJavaTypeAdapter(value = ValidatorClassAdapter.class)
	public Class<? extends Validator<?>> getValidator() {
		return validator;
	}

	public void setValidator(Class<? extends Validator<?>> validator) {
		this.validator = validator;
	}

	@XmlAttribute(required = false)
	@XmlJavaTypeAdapter(value = ConverterClassAdapter.class)
	public Class<? extends Converter<?>> getConverter() {
		return converter;
	}

	public void setConverter(Class<? extends Converter<?>> converter) {
		this.converter = converter;
	}

	@XmlAttribute(required = false)
	public boolean isAjaxValidator() {
		return ajaxValidator;
	}

	public void setAjaxValidator(boolean ajaxValidator) {
		this.ajaxValidator = ajaxValidator;
	}

	@XmlAttribute(required = false)
	@XmlJavaTypeAdapter(value = XmlClassAdapter.class)
	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	@XmlAttribute
	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	@XmlAttribute
	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	@XmlAttribute
	public String getDecimalMin() {
		return decimalMin;
	}

	public void setDecimalMin(String decimalMin) {
		this.decimalMin = decimalMin;
	}

	@XmlAttribute
	public String getDecimalMax() {
		return decimalMax;
	}

	public void setDecimalMax(String decimalMax) {
		this.decimalMax = decimalMax;
	}

	@XmlAttribute
	@XmlJavaTypeAdapter(value = XmlPatternAdapter.class)
	public Pattern getRegex() {
		return regex;
	}

	public void setRegex(Pattern regex) {
		this.regex = regex;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
