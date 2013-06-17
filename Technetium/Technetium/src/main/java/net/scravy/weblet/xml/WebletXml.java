package net.scravy.weblet.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.scravy.weblet.Handler;
import net.scravy.weblet.Module.HandlerXml;

@XmlRootElement(name = "weblet")
public class WebletXml {
	
	private Class<? extends Handler> defaultHandler;
	
	private List<ModuleXml> modules = new ArrayList<ModuleXml>();
	
	private List<HandlerXml> handlers = new ArrayList<HandlerXml>();
	
	private String name;

	@XmlElementWrapper(name = "modules")
	@XmlElements(value = @XmlElement(name = "module"))
	public List<ModuleXml> getModules() {
		return modules;
	}

	public void setModules(List<ModuleXml> modules) {
		this.modules = modules;
	}

	@XmlElementWrapper(name = "handlers")
	@XmlElements(value = @XmlElement(name = "handler"))
	public List<HandlerXml> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<HandlerXml> handlers) {
		this.handlers = handlers;
	}

	@XmlAttribute(required = true)
	@XmlJavaTypeAdapter(value = HandlerClassAdapter.class)
	public Class<? extends Handler> getDefaultHandler() {
		return defaultHandler;
	}

	public void setDefaultHandler(Class<? extends Handler> defaultHandler) {
		this.defaultHandler = defaultHandler;
	}

	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}