package net.scravy.weblet.forms;

import java.net.URL;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.scravy.technetium.util.iterator.IteratorUtil;
import net.scravy.technetium.util.value.Either;
import net.scravy.weblet.GenericResponse;
import net.scravy.weblet.Handler;
import net.scravy.weblet.Module;
import net.scravy.weblet.Module.HandlerXml;
import net.scravy.weblet.Request;
import net.scravy.weblet.Response;
import net.scravy.weblet.Response.Status;
import net.scravy.weblet.User;
import net.scravy.weblet.Weblet;
import net.scravy.weblet.forms.Validator.Validation.Okay;
import net.scravy.weblet.transform.TransformerWeblet;
import net.scravy.weblet.xml.ModuleXml;
import net.scravy.weblet.xml.TechnetiumXml;
import net.scravy.weblet.xml.WebletXml;

import org.w3c.dom.Element;

import com.google.common.base.Joiner;

/**
 * A {@link Weblet} that supports modules and forms.
 * <p>
 * A FormsWeblet is configured via a <code>technetium.xml</code> file in the
 * <code>META-INF</code> directory (just as JPA persistence units are). It
 * decides which request is handled by what handler. First and foremost it is
 * capable of validating forms. Only validated forms will make it to their
 * corresponding {@link FormHandler}, others are rejected and automatically
 * handled.
 * </p>
 * 
 * @see Form
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class FormsWeblet extends TransformerWeblet {

	private static final long serialVersionUID = 5422930798998486934L;

	private final Registry registry = new Registry();
	private String webletName;
	private final JAXBContext jaxbFormsContext;

	public FormsWeblet(String weblet, String persistenceUnit) {
		super(persistenceUnit);

		this.webletName = weblet;
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(ValidationReport.class);
		} catch (JAXBException exc) {
			logger.warn("Could not create JAXBContext for FormsWeblet!", exc);
		}
		this.jaxbFormsContext = context;
	}

	protected Response validate(Request request) throws Exception {
		Response response = new GenericResponse();

		return response;
	}

	protected Response processForm(Request request) throws Exception {
		String formName = request.getString("!form");
		FormProcessor processor = registry.getFormProcessor(formName);
		if (processor == null) {
			throw new FormProcessorException(String.format(
					"No FormProcessor found for handling “%s”.", formName));
		}
		Either<Okay, ValidationReport> result = processor
				.process(this, request);

		Response response = new GenericResponse();
		Element formElement = response.createElement("form");
		formElement.setAttribute("name", formName);

		if (result.isLeft()) {
			formElement.setAttribute("validation", "okay");
		} else {
			ValidationReport violations = result.getRight();
			formElement.setAttribute("validation", "nope");
			jaxbFormsContext
					.createMarshaller()
					.marshal(violations, formElement);
		}
		response.append(formElement);

		return response;

	}

	/**
	 * Loads modules and sets up handlers, after calling
	 * {@link TransformerWeblet#init(ServletConfig) init() from super class.}.
	 * <p>
	 * This method particularly seeks for <code>technetium.xml</code> in certain
	 * known places, such as <code>META-INF/</code>, and loads and registers any
	 * handlers and/or modules defined or referenced there.
	 * </p>
	 * 
	 * @see TechnetiumXml
	 * @see Registry
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		registry.configure(properties);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(
					TechnetiumXml.class, WebletXml.class, ModuleXml.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			// iterate through all technetium.xml-files found.
			for (URL url : IteratorUtil.iterate(getClass().getClassLoader()
					.getResources("META-INF/technetium.xml"))) {
				TechnetiumXml xml = (TechnetiumXml) unmarshaller
						.unmarshal(url.openStream());
				// seeks WebletXml with our name
				for (WebletXml webletXml : xml.getWeblets()) {
					if (webletName.equals(webletXml.getName())) {
						registry.registerDefaultHandler(
								webletXml.getDefaultHandler());
						// loads handlers
						for (HandlerXml handlerXml : webletXml.getHandlers()) {
							registry.registerHandler(
									handlerXml.getPath(),
									handlerXml.getHandler());
						}
						// loads modules
						for (ModuleXml moduleXml : webletXml.getModules()) {
							registry.loadModule(moduleXml.getDescriptor());
						}
					}
				}
			}
		} catch (Exception exc) {
			logger.warn(
					String.format(
							"%s whilst trying to configure Weblet “%s”.",
							exc.getClass().getSimpleName(), webletName), exc);
		}
	}

	@Override
	protected Response handleRequest(Request request) throws Exception {

		if (request.has("!validate")) {
			return validate(request);
		} else if (request.has("!form")) {
			return processForm(request);
		}

		Response response = new GenericResponse();
		String[] path = request.getPath();

		response.set("root", request.getRootPath());
		response.set("path", Joiner.on('/').join(request.getPath()));
		response.set("rand", Math.random());

		Handler handler = registry.handlerFor(path);
		handler.handle(this, request, response);

		User p = request.getSession().getUser();

		if (response.getStatus().equals(Status.NOT_FOUND)) {
			response.setTemplate("error404");
			response.setContentType("text/html");
		}

		if (p == null) {
			response.set("loggedIn", false);
		} else {
			response.set("loggedIn", true);

			Element e = response.createElement("user");

			try {
				Marshaller marshaller = context.get().createMarshaller();
				marshaller.marshal(p, e);

				response.append(e);
			} catch (Exception exc) {
				logger.warn("JAXB failure while marshalling user.", exc);
			}
		}

		return response;

	}

	@Override
	public SortedMap<String[], Handler> getHandlers() {
		return registry.getRegisteredHandlers();
	}

	@Override
	public Map<String, Module> getLoadedModules() {
		return registry.getLoadedModules();
	}

	public FormDefinition getFormDefinition(String name) {
		return registry.getFormProcessor(name).getFormDefinition();
	}
}