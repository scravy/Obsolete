package net.scravy.weblet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenericResponse implements Response {

	final private Map<String, Object> values = new HashMap<String, Object>();
	final private ArrayList<Exception> exceptions = new ArrayList<Exception>(4);

	private OutputWriter outputWriter = null;
	private String contentType = "text/html";
	private String template = "default";
	private String redirect = null;
	private Status status = Status.OK;

	private final Document document;

	public GenericResponse() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.newDocument();
			document.appendChild(document.createElement("response"));
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void addException(Exception exc) {
		exceptions.add(exc);

		document.getDocumentElement().appendChild(
				document.createElement(exc.getClass().getSimpleName()));
	}

	@Override
	public <T> void set(String key, T value) {
		values.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, T defaultValue) {
		if (!values.containsKey(key)) {
			return defaultValue;
		}
		Object value = values.get(key);
		if (value == null) {
			return defaultValue;
		}

		try {
			return (T) value;
		} catch (ClassCastException exc) {
			return defaultValue;
		}
	}

	@Override
	public String getString(String key) {
		if (!values.containsKey(key)) {
			return "";
		}
		Object value = values.get(key);
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	@Override
	public Set<String> getKeys() {
		return values.keySet();
	}

	@Override
	public void setTemplate(String templateName) {
		if (templateName == null) {
			throw new IllegalArgumentException("templateName must not be null");
		}
		this.template = templateName;
	}

	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public Document getDocument() {
		try {
			return document;
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	public void setOutputWriter(OutputWriter outputWriter) {
		this.outputWriter = outputWriter;
	}

	@Override
	public OutputWriter getOutputWriter() {
		return outputWriter;
	}

	@Override
	public boolean hasOutputWriter() {
		return outputWriter != null;
	}

	@Override
	public void setContentType(String contentType) {
		if (contentType == null) {
			throw new IllegalArgumentException();
		}
		this.contentType = contentType;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setRedirect(String relativePath) {
		this.redirect = relativePath;
	}

	@Override
	public boolean hasRedirect() {
		return redirect != null;
	}

	@Override
	public String getRedirect() {
		return redirect;
	}

	@Override
	public void append(Element element) {
		document.getDocumentElement().appendChild(element);
	}

	@Override
	public Element createElement(String name) {
		return document.createElement(name);
	}

	@Override
	public Element createElement(String name, String text) {
		Element e = document.createElement(name);
		e.appendChild(document.createTextNode(text));
		return e;
	}

	@Override
	public Element createElement(Element parent, String name) {
		Element e = createElement(name);
		parent.appendChild(e);
		return e;
	}

	@Override
	public Element createElement(Element parent, String name, String text) {
		Element e = createElement(name, text);
		parent.appendChild(e);
		return e;
	}

	@Override
	public Object get(String key) {
		return values.get(key);
	}

	@Override
	public void setStatus(Status status) {
		if (status == null) {
			throw new IllegalArgumentException("`status` may not be null");
		}
		this.status = status;
	}

	@Override
	public Status getStatus() {
		return status;
	}
}
