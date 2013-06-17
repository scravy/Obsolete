package net.scravy.weblet.transform;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

public class Resolver implements URIResolver {

	private final XSLLoader loader;

	public Resolver(XSLLoader loader) {
		this.loader = loader;
	}

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		if (base == null) {
			if (href.endsWith(".xsl")) {
				return loader.load(href.substring(0, href.length() - 4));
			}
		}

		return null;
	}

}
