package net.scravy.weblet.forms;

import net.scravy.weblet.xml.FormXml;

/**
 * A FormProcessorException is thrown when a FormProcessor is created with
 * an illegal {@link FormXml} definition.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("serial")
public class FormProcessorException extends Exception {

	public FormProcessorException() {
		super();
	}

	public FormProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormProcessorException(String message) {
		super(message);
	}

	public FormProcessorException(Throwable cause) {
		super(cause);
	}
}