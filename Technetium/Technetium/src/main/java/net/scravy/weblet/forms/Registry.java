package net.scravy.weblet.forms;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.scravy.technetium.util.EnhancedProperties;
import net.scravy.weblet.Handler;
import net.scravy.weblet.Module;
import net.scravy.weblet.script.CompiledScriptHandler;
import net.scravy.weblet.script.ScriptHandler;
import net.scravy.weblet.xml.FormXml;

import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.jruby.embed.jsr223.JRubyEngineFactory;
import org.python.jsr223.PyScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clojure.contrib.jsr223.ClojureScriptEngineFactory;

/**
 * The HandlerRegistry is responsible for loading and maintaining
 * {@link Handler}s throughout the lifecycle of a weblet.
 * <p>
 * The current implementation is able to load modules from XML specifications as
 * defined via {@link Module}.
 * </p>
 * <p>
 * Additionally a number of scripting engines is supported. At the moment these
 * are Python, Ruby, Groovy, and Clojure natively, as well as other scripting
 * engines which can be found on the system. The OpenJDK from Sun/Oracle
 * contains Mozilla Rhino. The current implementation can utilize every
 * ScriptingEngine as discovered by
 * {@link ScriptEngineManager#getEngineByExtension(String)}, where an engine is
 * detected by the file name extension of the script file to load.
 * </p>
 * <p>
 * ISSUE: While clojure could be compiled (the
 * {@link ClojureScriptEngineFactory} creates {@link Compilable} instances of
 * {@link ScriptEngine}, the implementation seems to be broken. Thus, Clojure
 * scripts will <b>always</b> be newly evaluated on each request.
 * </p>
 * <p>
 * There is a configuration option –
 * <code>technetium.scriptloader.precompile</code> – which is a boolean value.
 * If true, scripts are precompiled iff the engine is {@link Compilable} (except
 * for clojure, see above). If false, scripts are evaluated on each invocation
 * (which is what you want in development mode, but not in deployment).
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
class Registry {

	private final PathTrie<Handler> handlers = new PathTrie<Handler>();
	private final Map<String, ScriptEngine> scriptEngines = new ConcurrentHashMap<String, ScriptEngine>();
	private final Map<String, FormProcessor> forms = new ConcurrentHashMap<String, FormProcessor>();
	private final Map<String, Module> loadedModules = new ConcurrentHashMap<String, Module>();

	private final Unmarshaller unmarshaller;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private ScriptEngine getScriptEngine(String extension) {
		if (!scriptEngines.containsKey(extension)) {
			if ("py".equals(extension)) {
				scriptEngines.put("py",
						new PyScriptEngineFactory().getScriptEngine());
			} else if ("rb".equals(extension)) {
				scriptEngines.put("rb",
						new JRubyEngineFactory().getScriptEngine());
			} else if ("groovy".equals(extension)) {
				scriptEngines.put("groovy",
						new GroovyScriptEngineFactory().getScriptEngine());
			} else if ("clj".equals(extension)) {
				scriptEngines.put("clj",
						new ClojureScriptEngineFactory().getScriptEngine());
			} else {
				scriptEngines.put(extension, new ScriptEngineManager()
						.getEngineByExtension(extension));
			}
		}
		return scriptEngines.get(extension);
	}

	@SuppressWarnings("serial")
	private EnhancedProperties properties = new EnhancedProperties() {
		{
			setProperty("technetium.scriptloader.precompile", true);
		}
	};

	public void configure(Properties properties) {
		this.properties.merge(properties);
	}

	Registry() {
		Unmarshaller u = null;
		try {
			u = JAXBContext.newInstance(Module.class).createUnmarshaller();
		} catch (JAXBException exc) {
			logger.warn(
					"JAXB-Unmarshaller for Module-XML could not be created.",
					exc);
		}
		unmarshaller = u;
	}

	private static String[] makePathArray(String path) {
		if (path == null) {
			return new String[0];
		}
		int i = 0;
		while (i < path.length() && path.charAt(i) == '/') {
			i++;
		}
		path = path.substring(i);
		if (path.isEmpty()) {
			return new String[0];
		}
		return path.split("/+");
	}

	public void registerDefaultHandler(Handler handler) {
		handlers.setDefaultValue(handler);
	}

	public boolean registerDefaultHandler(Class<? extends Handler> handlerClass) {
		try {
			handlers.setDefaultValue(handlerClass.newInstance());
			return true;
		} catch (Exception exc) {
		}
		return false;
	}

	public boolean registerHandler(String path, Handler handler) {
		logger.debug(String.format("Registering handler %s at %s",
				handler.getClass().getName(), path));
		handlers.put(makePathArray(path), handler);
		return true;
	}

	public boolean registerHandler(String path,
			Class<? extends Handler> handlerClass) {
		try {
			return registerHandler(path, handlerClass.newInstance());
		} catch (Exception exc) {
			return false;
		}
	}

	public Handler handlerFor(String[] path) {
		return handlers.get(path);
	}

	public boolean loadModule(Module module) {
		boolean success = true;

		for (Module.HandlerXml h : module.getHandlers()) {
			// register handlers of this module

			if (h.getScript() == null) {
				// register a java handler
				try {
					success = success
							&& registerHandler(h.getPath(), h.getHandler());
				} catch (Exception exc) {
					success = false;
					logger.warn(
							String.format(
									"Loading a module failed: Handler “%s” failed to load.",
									h.getHandler().getCanonicalName()), exc);
				}
			} else {
				// register a script handler
				try {
					final String script = h.getScript();
					String ext = script.substring(script.lastIndexOf('.') + 1);
					final ScriptEngine engine = getScriptEngine(ext);

					if (engine == null) {
						throw new ScriptException(
								String.format(
										"No suitable Engine found for “%s” (is the file extension correct?).",
										script));
					}
					Handler handler = properties.get(
							"technetium.scriptloader.precompile", true)
							&& engine instanceof Compilable
							&& !(engine.getFactory() instanceof ClojureScriptEngineFactory)
							? new CompiledScriptHandler(engine, script)
							: new ScriptHandler(engine, script);

					registerHandler(h.getPath(), handler);
				} catch (Exception exc) {
					logger.warn(String.format(
							"Error registering script handler at “%s”",
							h.getPath()), exc);
					success = false;
				}
			}
		}
		if (success) {
			// if all modules were loaded successfully,
			// import form definitions and create form processors
			for (FormXml formXml : module.getForms()) {
				String formName = module.getName() + '.' + formXml.getName();

				try {
					forms.put(formName,
							new FormProcessor(new FormDefinition(formName,
									formXml)));
				} catch (Exception exc) {
					logger.warn(
							String.format(
									"Failed to initialize FormProcessor for form “%s”.",
									formName), exc);
					success = false;
				}
			}
			loadedModules.put(module.getName(), module);
		}
		return success;
	}

	public boolean loadModule(String moduleXmlResourcePath) {
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(
				moduleXmlResourcePath);
		if (inStream == null) {
			logger.warn(String.format("Could not find resource “%s”.",
					moduleXmlResourcePath));
			return false;
		}
		try {
			Module module = (Module) unmarshaller.unmarshal(inStream);
			return loadModule(module);
		} catch (Exception exc) {
			logger.warn(
					String.format(
							"Loading a module failed: Unmarshalling resource “%s” failed.",
							moduleXmlResourcePath), exc);
			return false;
		}
	}

	public SortedMap<String[], Handler> getRegisteredHandlers() {
		return Collections.unmodifiableSortedMap(handlers.entries);
	}

	public Map<String, Module> getLoadedModules() {
		return Collections.unmodifiableMap(loadedModules);
	}

	/**
	 * Returns the FormProcessor with the given name.
	 * 
	 * @param name
	 *            The name of the FormProcessor.
	 * @return The FormProcessor or null, of no such FormProcessor exists.
	 */
	public FormProcessor getFormProcessor(String name) {
		return forms.get(name);
	}

	/**
	 * Returns a Collection of all registered FormProcessors.
	 * 
	 * @return A Collection of all registered FormProcessors.
	 */
	public Collection<FormProcessor> getFormProcessors() {
		return forms.values();
	}
}
