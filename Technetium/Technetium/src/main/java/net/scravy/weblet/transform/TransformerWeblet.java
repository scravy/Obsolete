package net.scravy.weblet.transform;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.persistence.metamodel.EntityType;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.scravy.technetium.util.value.ValueUtil;
import net.scravy.weblet.AbstractWeblet;
import net.scravy.weblet.GenericResponse;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.xml.ExceptionXml;

import org.json.JSONException;
import org.json.JSONWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Implements functionality to translate a {@link Request} into an actual
 * {@link HttpServletRequest} using XSL-T Templates.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public abstract class TransformerWeblet extends AbstractWeblet {

	private static final long serialVersionUID = 6837832377373685126L;

	private TransformerPool transformers;

	protected final ThreadLocal<JAXBContext> context = new ThreadLocal<JAXBContext>() {
		@Override
		protected JAXBContext initialValue() {
			try {
				return JAXBContext.newInstance(persistenceClasses);
			} catch (Exception exc) {
				throw new RuntimeException(
						"JAXB-Context for Servlet could not be created.",
						exc);
			}
		}
	};

	private Class<?>[] persistenceClasses = new Class<?>[0];

	private final ThreadLocal<DOMImplementationLS> domImpl = new ThreadLocal<DOMImplementationLS>() {
		@Override
		protected DOMImplementationLS initialValue() {
			try {
				DOMImplementationRegistry reg = DOMImplementationRegistry
						.newInstance();
				return (DOMImplementationLS) reg.getDOMImplementation("LS");
			} catch (Exception exc) {
				logger.warn("DOMImplementationLS could not be created", exc);
			}
			return super.initialValue();
		}
	};

	protected TransformerWeblet(String persistenceUnit) {
		super("technetium");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String poolClass = properties.get("technetium.transformers.pool",
				CachingTransformerPool.class.getCanonicalName());

		transformers = TransformerPoolFactory.newInstance(this)
				.newTransformerPool(poolClass);

		Set<EntityType<?>> entities = db().getMetamodel().getEntities();
		persistenceClasses = new Class<?>[entities.size()];
		int i = 0;
		for (EntityType<?> entity : entities) {
			persistenceClasses[i] = entity.getJavaType();
			i++;
		}
	}

	@Override
	protected void doResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Response response)
			throws IOException {

		try {
			String contentType = response.getContentType();

			String type = ValueUtil
					.maybe(httpRequest.getParameter("!type"), "");
			if (type.equals("xml")) {
				contentType = "text/xml";
			} else if (type.equals("json")) {
				contentType = "application/json";
			}
			if (response.hasRedirect()) {

				String root = httpRequest.getContextPath()
						+ httpRequest.getServletPath();
				String target = root + '/' + response.getRedirect();
				httpResponse.sendRedirect(target);

			} else {
				switch (response.getStatus()) {
				case NOT_FOUND:
					httpResponse.setStatus(404);
				}

				httpResponse.setContentType(contentType);
				if (contentType.startsWith("text/")
						|| contentType.endsWith("/json")
						|| contentType.endsWith("/xml")
						|| contentType.endsWith("+xml")) {
					httpResponse.setCharacterEncoding("UTF-8");
				}

				if (response.hasOutputWriter()) {

					OutputStream out = getOutputStream(httpRequest,
							httpResponse);
					response.getOutputWriter().write(out);
					out.close();

				} else if (contentType.equals("application/json")) {

					Element element = augmentedDocument(response)
							.getDocumentElement();
					JSONWriter writer = new JSONWriter(httpResponse.getWriter());

					writeJSON(writer, element);

				} else if (contentType.equals("text/xml")) {

					OutputStream out = getOutputStream(httpRequest,
							httpResponse);
					LSOutput output = domImpl.get().createLSOutput();
					output.setByteStream(out);
					LSSerializer serializer = domImpl.get()
							.createLSSerializer();
					serializer.write(augmentedDocument(response), output);
					out.close();

				} else {

					Transformer transformer = transformers.get(response
							.getTemplate());
					if (transformer == null) {
						transformer = transformers
								.get("error-missing-template");
						transformer.setParameter("template",
								response.getTemplate());
					}
					OutputStream out = getOutputStream(httpRequest,
							httpResponse);
					Document document = response.getDocument();
					Source source = new DOMSource(document);
					Result result = new StreamResult(out);

					for (String key : response.getKeys()) {
						transformer.setParameter(key, response.get(key));
					}
					transformer.transform(source, result);
					out.close();
				}
			}
		} catch (Exception exc) {
			httpResponse.sendError(500, "Internal Server Error: "
					+ exc.getClass().getSimpleName());
			logger.warn("Unhandled Exception while handling response", exc);
			throw new IOException(exc);
		}
	}

	private static OutputStream getOutputStream(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean x = true;

		OutputStream out = response.getOutputStream();

		if (x) {
			response.setHeader("Content-Encoding", "gzip");
			out = new GZIPOutputStream(out);
		}

		return out;
	}

	private static Document augmentedDocument(Response response) {
		Document doc = response.getDocument();
		Element root = doc.getDocumentElement();

		for (String key : response.getKeys()) {
			root.setAttribute(key, response.getString(key));
		}

		return doc;
	}

	private static void writeJSON(JSONWriter writer, Element element)
			throws JSONException {
		writer.object().key("!").value(element.getTagName());

		NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node n = attrs.item(i);
			writer.key(n.getNodeName()).value(n.getNodeValue());
		}
		NodeList children = element.getChildNodes();
		if (children.getLength() > 0) {
			writer.key("*").array();
			for (int i = 0; i < children.getLength(); i++) {
				Node n = children.item(i);
				if (n instanceof Element) {
					writeJSON(writer, (Element) n);
				} else if (n instanceof Text) {
					writer.value(((Text) n).getTextContent());
				}
			}
			writer.endArray();
		}

		writer.endObject();
	}

	@Override
	public Marshaller createMarshaller() {
		try {
			return context.get().createMarshaller();
		} catch (Exception exc) {
			throw new RuntimeException(
					"JAXB Exception: Could not create Marshaller.", exc);
		}
	}

	@Override
	protected Response makeErrorResponse(Exception exc) {
		Response response = new GenericResponse();

		response.setTemplate("error500");

		Element e = response.createElement("exceptions");
		try {
			Marshaller m = JAXBContext
					.newInstance(ExceptionXml.class)
					.createMarshaller();

			m.marshal(new ExceptionXml(exc), e);
		} catch (Exception crap) {
			Element f = response.createElement(e, "fatalException",
					exc.getMessage());
			f.setAttribute("type", exc.getClass().getCanonicalName());
			f.setAttribute("name", exc.getClass().getSimpleName());
			Element o = response.createElement(e, "originalException",
					crap.getMessage());
			o.setAttribute("type", crap.getClass().getCanonicalName());
			o.setAttribute("name", crap.getClass().getSimpleName());
		}
		response.append(e);

		return response;
	}

}
