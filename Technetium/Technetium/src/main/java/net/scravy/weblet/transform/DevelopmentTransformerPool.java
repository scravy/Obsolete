package net.scravy.weblet.transform;

import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import net.scravy.weblet.Weblet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Always fetches templates from disk, without caching - slow, but useful for
 * developing and immediate testing.
 */
public class DevelopmentTransformerPool implements TransformerPool {

	private final XSLLoader loader;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	DevelopmentTransformerPool(Weblet parent) throws IOException {
		loader = new XSLLoader(parent);
	}

	@Override
	public Transformer get(final String name) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();

			factory.setURIResolver(new Resolver(loader));

			Transformer transformer = factory.newTransformer(loader.load(name));

			return transformer;
		} catch (Exception exc) {
			logger.warn("Error on creating an XSL-Transformer for template "
					+ name + "", exc);
			return null;
		}
	}
}
