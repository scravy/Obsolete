package net.scravy.weblet;

import java.io.IOException;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.scravy.persistence.EntityManager;
import net.scravy.persistence.EntityManagerWrapper;
import net.scravy.technetium.util.EnhancedProperties;
import net.scravy.technetium.util.UnmodifiableProperties;
import net.scravy.technetium.util.iterator.IteratorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides basic functionality which any {@link Weblet} should have (mainly JPA
 * connectivity).
 * <p>
 * Developers should extend (subclass) this class. It is mandatory that you
 * either pass the name of a {@link #AbstractWeblet(String) persistence unit} or
 * an {@link #AbstractWeblet(EntityManagerFactory) EntityManagerFactory} to the
 * constructor. The weblet will than provide thread-safe access to the database
 * using JPA via {@link #db()}.
 * </p>
 * <p>
 * Furthermore this implementation will expose a final protected {@link #logger}
 * and {@link #properties}, which will be filled on {@link #init()} with values
 * from {@link ServletConfig#getInitParameter(String)}.
 * </p>
 * <p>
 * This Weblet unifies the way requests are handled in a traditional
 * {@link Servlet}. Instead of forcing you to override methods for each HTTP
 * method, you need to implement just three methods:
 * </p>
 * <dl>
 * <dt>{@link #handleRequest(Request)}</dt>
 * <dd>Creates a {@link Response} based on a {@link Request}.</dd>
 * <dt>
 * {@link #doResponse(HttpServletRequest, HttpServletResponse, Response)}</dt>
 * <dd>Translates a {@link Response} into a the given
 * {@link HttpServletResponse}.</dd>
 * <dt>{@link #makeErrorResponse(Exception)}</dt>
 * <dd>Creates a {@link Response} suitable to explain an internal server error
 * (error 500).</dd>
 * </dl>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public abstract class AbstractWeblet extends HttpServlet implements Weblet {

	private static final long serialVersionUID = -5712530156581640780L;

	/**
	 * A logger to be used with this weblet.
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private EntityManagerFactory emf;
	private ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();

	private final String persistenceUnit;

	/**
	 * Configuration properties of this weblet.
	 */
	protected final EnhancedProperties properties = new EnhancedProperties();

	/**
	 * A read-only view on the Configuration properties of this weblet.
	 */
	private final UnmodifiableProperties readOnlyProperties =
			new UnmodifiableProperties(properties);

	/**
	 * Initializes this weblet with the name of a persistence unit, which is
	 * then to be read from persistence.xml.
	 * 
	 * @param persistenceUnit
	 *            The name of the persistence unit.
	 */
	public AbstractWeblet(String persistenceUnit) {
		if (persistenceUnit == null || persistenceUnit.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.persistenceUnit = persistenceUnit;
	}

	/**
	 * Initializes this weblet using a given {@link EntityManagerFactory}.
	 * 
	 * @param emf
	 *            The EntityManagerFactory.
	 */
	public AbstractWeblet(EntityManagerFactory emf) {
		if (emf == null) {
			throw new IllegalArgumentException();
		}

		this.persistenceUnit = null;
		this.emf = emf;
	}

	protected abstract Response makeErrorResponse(Exception exc);

	protected abstract void doResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Response response)
			throws IOException;

	protected abstract Response handleRequest(Request request) throws Exception;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		for (String name : IteratorUtil.iterate(config.getInitParameterNames(),
				String.class)) {
			properties.setProperty(name, config.getInitParameter(name));
		}

		if (persistenceUnit != null) {
			emf = Persistence.createEntityManagerFactory(persistenceUnit,
					properties);
		}
	}

	@Override
	public EntityManager db() {
		EntityManager e = em.get();
		if (e == null) {
			e = EntityManagerWrapper.wrap(emf.createEntityManager());
			em.set(e);
		}
		return e;
	}

	@Override
	public void destroy() {
		emf.close();

		super.destroy();
	}

	private void processRequest(
			RequestMethod method,
			HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {

		Response response;
		Request request = new GenericRequest(method, httpRequest);

		try {
			response = handleRequest(request);
		} catch (Exception exc) {
			response = makeErrorResponse(exc);
		}

		EntityManager e = em.get();
		if (e != null) {
			try {
				if (e.getTransaction().isActive()) {
					e.getTransaction().rollback();
				}
			} catch (Exception exc) {
				response.addException(exc);
			} finally {
				try {
					em.remove();
				} catch (Exception exc) {
					response.addException(exc);
				} finally {
					e.close();
				}
			}
		}

		doResponse(httpRequest, httpResponse, response);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(RequestMethod.GET, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(RequestMethod.HEAD, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(RequestMethod.POST, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(RequestMethod.GET, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doDelete(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		processRequest(RequestMethod.DELETE, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doOptions(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		processRequest(RequestMethod.OPTIONS, req, resp);
	}

	/**
	 * Delegates to
	 * {@link #processRequest(RequestMethod, HttpServletRequest, HttpServletResponse)}
	 * .
	 */
	@Override
	final protected void doTrace(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		processRequest(RequestMethod.TRACE, req, resp);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> clazz) {
		if (clazz.isAssignableFrom(getClass())) {
			return (T) this;
		}
		return null;
	}

	@Override
	public TimeZone getDefaultTimeZone() {
		return TimeZone.getDefault();
	}

	@Override
	public TimeZone getTimeZoneForRequest(Request request) {
		Session s = request.getSession();
		if (s != null) {
			User u = s.getUser();
			if (u != null) {
				TimeZone z = u.getUserTimeZone();
				if (z != null) {
					return z;
				}
			}
		}
		return getDefaultTimeZone();
	}

	@Override
	public UnmodifiableProperties getProperties() {
		return readOnlyProperties;
	}
}
