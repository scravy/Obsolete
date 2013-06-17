package net.scravy.weblet.forms;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.scravy.weblet.xml.ExceptionXml;

/**
 * A ValidationViolation is a tuple holding the name of a field which failed to
 * validate and the kind of error (a String identifying the problem such as
 * “malformed”).
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "violation")
public class ValidationViolation implements Serializable {
	private final String messageCode;
	private final String fieldName;
	private final ExceptionXml exception;

	ValidationViolation() {
		// JAXB needs a no-arg default constructor.
		messageCode = null;
		fieldName = null;
		exception = null;
	}

	/**
	 * The default constructor.
	 * 
	 * @param fieldName
	 *            The name of the field which failed to validate.
	 * @param messageCode
	 *            The error code of the validation error.
	 */
	public ValidationViolation(String fieldName, String messageCode) {
		this.fieldName = fieldName;
		this.messageCode = messageCode;
		this.exception = null;
	}

	/**
	 * The constructor for a validation which caused an Exception.
	 * 
	 * @param fieldName
	 *            The name of the field which failed to validate.
	 * @param exc
	 *            The caused exception.
	 */
	public ValidationViolation(String fieldName,
			Exception exc) {
		this.fieldName = fieldName;
		this.messageCode = "EXCEPTION";
		this.exception = new ExceptionXml(exc);
	}

	@XmlAttribute(required = true)
	public String getMessageCode() {
		return messageCode;
	}

	@XmlAttribute(required = true)
	public String getFieldName() {
		return fieldName;
	}

	public boolean hasException() {
		return exception != null;
	}

	@XmlElement(required = false)
	public ExceptionXml getException() {
		return exception;
	}
}