package net.scravy.weblet;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A response object which contains only pure information about the response;
 * responses are built around a {@link Document DOM (XML) Document}.
 * 
 * @author Julian Fleischer
 * @version 1.0
 */
public interface Response {

	enum Status {
		OK, NOT_FOUND, FORBIDDEN, NOT_AUTHORIZED
	}

	/**
	 * Adds another Exception to this Response object. This may be used by a
	 * Weblet to inform the user about some minor problems which occured during
	 * the processing of this request.
	 * 
	 * @param exc
	 *            The Exception that occured.
	 */
	void addException(Exception exc);

	/**
	 * Sets an output variable.
	 * 
	 * @param name
	 *            The name of the output variable.
	 * @param value
	 *            The value of the output variable.
	 */
	<T> void set(String name, T value);

	/**
	 * Retrieve the value of a given output variable.
	 * 
	 * @param name
	 *            The name of the output variable.
	 * @param defaultValue
	 *            If the name is not associated with any value or the value is
	 *            not of the requested type, the defaultValue is returned. The
	 *            type of this argument determines the requested return type.
	 * @return The value associated with this variable name or the defaultValue,
	 *         if no value is associated with this name or the value associated
	 *         with this name is not compatible with the type of the requested
	 *         value (determined by the defaultValue).
	 */
	<T> T get(String name, T defaultValue);

	Object get(String key);

	Set<String> getKeys();

	String getString(String key);

	/**
	 * Set the template which should be used to render this response.
	 * 
	 * @param templateName
	 *            The name of the template. Something like “start”,
	 *            “Module/index”, etc.
	 */
	void setTemplate(String templateName);

	/**
	 * Return the name of the template which will be used to render this
	 * response.
	 * 
	 * @return The templates name or null, if none has been set yet.
	 */
	String getTemplate();

	Document getDocument();

	void setOutputWriter(OutputWriter outputWriter);

	OutputWriter getOutputWriter();

	boolean hasOutputWriter();

	void setContentType(String contentType);

	String getContentType();

	void setRedirect(String relativePath);

	String getRedirect();

	boolean hasRedirect();

	void append(Element node);

	Element createElement(String name);

	Element createElement(String name, String text);

	Element createElement(Element parent, String name);

	Element createElement(Element parent, String name, String text);

	void setStatus(Status status);

	Status getStatus();
}
