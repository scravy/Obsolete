package net.scravy.weblet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.scravy.weblet.xml.FormXml;
import net.scravy.weblet.xml.HandlerClassAdapter;


/**
 * A POJO for holding information about a module and serializing to/from XML.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@XmlRootElement
public class Module {

	public static class HandlerXml {

		private String path;

		private Class<? extends net.scravy.weblet.Handler> handler;

		private String script;

		public HandlerXml() {

		}

		public HandlerXml(String path,
				Class<? extends net.scravy.weblet.Handler> handler) {
			this.path = path;
			this.handler = handler;
		}

		@XmlAttribute(name = "path", required = true)
		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		@XmlAttribute(required = false)
		@XmlJavaTypeAdapter(value = HandlerClassAdapter.class)
		public Class<? extends net.scravy.weblet.Handler> getHandler() {
			return handler;
		}

		public void setHandler(
				Class<? extends net.scravy.weblet.Handler> handler) {
			this.handler = handler;
		}

		@XmlAttribute(required = false)
		public String getScript() {
			return script;
		}

		public void setScript(String script) {
			this.script = script;
		}
	}

	private List<Module.HandlerXml> handlers = new ArrayList<Module.HandlerXml>();

	private List<FormXml> forms = new ArrayList<FormXml>();

	private String name;

	public Module() {

	}

	public Module(Module.HandlerXml... handlers) {
		for (Module.HandlerXml handler : handlers) {
			this.handlers.add(handler);
		}
	}

	@XmlElementWrapper(name = "handlers")
	@XmlElements(value = { @XmlElement(name = "handler") })
	public List<Module.HandlerXml> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<Module.HandlerXml> handlers) {
		this.handlers = handlers;
	}

	@XmlElementWrapper(name = "forms")
	@XmlElements(value = { @XmlElement(name = "form") })
	public List<FormXml> getForms() {
		return forms;
	}

	public void setForms(List<FormXml> forms) {
		this.forms = forms;
	}

	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}