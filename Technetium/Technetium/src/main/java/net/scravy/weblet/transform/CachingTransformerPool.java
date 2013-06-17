package net.scravy.weblet.transform;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import net.scravy.weblet.Weblet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A caching TransformerPool, maintains a map of {@link Templates} and a
 * per-thread map of {@link Transformer}s, using {@link ThreadLocal}s.
 */
public class CachingTransformerPool implements TransformerPool {

	/**
	 * Access to the TransformerFactory occurs only when a new template is
	 * inserted into {@link #templates}, thus is always synchronized.
	 */
	private final TransformerFactory factory;

	/**
	 * Access to the Map of templates needs to be synchronized for writing,
	 * concurrent reading is safe.
	 */
	private final ConcurrentMap<String, Templates> templates =
			new ConcurrentHashMap<String, Templates>();

	/**
	 * The loader object which actually loads the XSL-Templates from disk.
	 */
	private final XSLLoader loader;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ThreadLocal<Map<String, Transformer>> transformers =
			new ThreadLocal<Map<String, Transformer>>() {
				@Override
				protected Map<String, Transformer> initialValue() {
					return new HashMap<String, Transformer>();
				}
			};

	CachingTransformerPool(Weblet parent) throws IOException {
		loader = new XSLLoader(parent);

		factory = TransformerFactory.newInstance();
		factory.setURIResolver(new Resolver(loader));
	}

	@Override
	public Transformer get(final String name) {
		Map<String, Transformer> transformers = this.transformers.get();

		if (transformers.containsKey(name)) {
			Transformer transformer = transformers.get(name);
			transformer.reset();
			return transformer;
		}

		try {
			if (!templates.containsKey(name)) {
				Source source = loader.load(name);
				Templates template = factory.newTemplates(source);
				templates.put(name, template);
			}
			Templates template = templates.get(name);
			Transformer transformer = template.newTransformer();
			transformers.put(name, transformer);
			return transformer;
		} catch (Exception exc) {
			logger.warn("Error on creating an XSL-Transformer for template "
					+ name + "", exc);
			return null;
		}

	}
}
