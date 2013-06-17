package net.scravy.weblet.xml;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exception")
public class ExceptionXml {

	public static class XMLStackTraceElement {

		private String className;
		private String fileName;
		private Integer lineNumber;
		private String methodName;
		
		public XMLStackTraceElement() {
		}

		@XmlAttribute(required = true)
		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		@XmlAttribute(required = false)
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		@XmlAttribute(required = false)
		public Integer getLineNumber() {
			return lineNumber;
		}

		public void setLineNumber(Integer lineNumber) {
			this.lineNumber = lineNumber;
		}

		@XmlAttribute(required = true)
		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public XMLStackTraceElement(StackTraceElement e) {
			className = e.getClassName();
			fileName = e.getFileName();
			lineNumber = e.getLineNumber();
			methodName = e.getMethodName();
		}

	}

	ExceptionXml() {
		// JAXB needs the empty constructor
	}

	public ExceptionXml(Throwable exc) {
		message = exc.getMessage();
		type = exc.getClass().getCanonicalName();
		setName(exc.getClass().getSimpleName());
		Throwable cause = exc.getCause();
		if (cause != null) {
			this.cause = new ExceptionXml(cause);
		}
		for (StackTraceElement e : exc.getStackTrace()) {
			stackTrace.add(new XMLStackTraceElement(e));
		}
	}

	private String type;

	private String name;

	private String message;

	private ExceptionXml cause;

	private List<XMLStackTraceElement> stackTrace = new ArrayList<XMLStackTraceElement>();

	@XmlAttribute(required = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(required = false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@XmlElement(name = "cause", required = false)
	public ExceptionXml getCause() {
		return cause;
	}

	public void setCause(ExceptionXml cause) {
		this.cause = cause;
	}

	@XmlElementWrapper(name = "stackTrace", required = true)
	@XmlElements(value = @XmlElement(name = "element"))
	public List<XMLStackTraceElement> getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(List<XMLStackTraceElement> stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
