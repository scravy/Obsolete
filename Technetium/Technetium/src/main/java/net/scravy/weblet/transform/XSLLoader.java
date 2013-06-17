package net.scravy.weblet.transform;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.FormsWeblet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class XSLLoader {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TransformerFactory transformerFactory = TransformerFactory
			.newInstance();
	private final Templates masterTemplate;

	private final String baseDir;

	private final Weblet parent;

	public XSLLoader(Weblet parent) throws IOException {

		this.parent = parent;

		baseDir = parent.getProperties()
				.getProperty("technetium.templates.dir");
		final String masterDir = parent.getProperties()
				.getProperty("technetium.templates.master.dir");

		String masterTemplatePath = masterDir + '/'
				+ parent.getProperties().getProperty(
						"technetium.templates.master.template");

		InputStream inputStream = getClass()
				.getClassLoader()
				.getResourceAsStream(masterTemplatePath);

		if (inputStream == null) {
			throw new FileNotFoundException(
					String.format(
							"The master template could not be loaded"
									+ " (%s – configured by technetium.templates.master.dir"
									+ " and technetium.templates.master.template)",
							masterTemplatePath));
		}

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);

			Source source = new SAXSource(new InputSource(inputStream));

			transformerFactory.setURIResolver(new URIResolver() {
				@Override
				public Source resolve(String href, String base)
						throws TransformerException {
					return new StreamSource(getClass().getClassLoader()
							.getResourceAsStream(masterDir + '/' + href));
				}
			});

			masterTemplate = transformerFactory.newTemplates(source);
		} catch (Exception exc) {
			throw new RuntimeException("Could not create master template.", exc);
		}
	}

	public Source load(String name) {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(baseDir + '/' + name + ".xsl");

		try {
			final Source source = new StreamSource(inputStream);

			final PipedOutputStream pipedOutputStream = new PipedOutputStream();
			final PipedInputStream pipedInputStream = new PipedInputStream(
					pipedOutputStream);

			final Result result = new StreamResult(pipedOutputStream);

			Thread transformer = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Transformer t = masterTemplate.newTransformer();
						t.setURIResolver(new MasterDocumentResolver(
								parent.unwrap(FormsWeblet.class)));
						t.transform(source, result);
					} catch (Exception exc) {
						logger.warn("Error applying master template.", exc);
						Thread.currentThread().interrupt();
					} finally {
						try {
							pipedOutputStream.close();
						} catch (IOException exc) {
							logger.warn(
									"Error closing output stream while loading XSLT.",
									exc);
						}
					}
				}
			});
			transformer.start();

			logger.debug(String.format("Loaded %s.", name));
			return new StreamSource(pipedInputStream);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}
}
