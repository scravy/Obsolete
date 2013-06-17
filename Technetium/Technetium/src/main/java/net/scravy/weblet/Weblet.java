package net.scravy.weblet;

import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;

import javax.xml.bind.Marshaller;

import net.scravy.persistence.EntityManager;
import net.scravy.technetium.util.UnmodifiableProperties;

/**
 * A Weblet is a simplified servlet, specialized for web application
 * development.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public interface Weblet {

	/**
	 * Returns the {@link EntityManager} associated with this Weblet.
	 * <p>
	 * This method is thread-safe, however, the obtained EntityManager is not.
	 * Calling this method twice from the same thread will return the same
	 * EntityManager (except it has been closed intermediately).
	 * </p>
	 * <p>
	 * The EntityManager returned is open, i.e. {@link EntityManager#isOpen}
	 * will return true on the returned instance. This implies that if an entity
	 * manager obtained with this method is closed, a new entity manager will be
	 * returned by this method (which is the exception mentioned above).
	 * </p>
	 * 
	 * @since 1.0
	 * @return This Weblets EntityManager.
	 */
	EntityManager db();

	/**
	 * Return an object of the specified type to allow access to a specific API.
	 * If this instance does not support the specified class, null is returned.
	 * 
	 * @param servletClass
	 *            The requested API.
	 * @return An instance of this Weblet which is of the specified class (if
	 *         this Weblet implements the requested class or interface).
	 * 
	 * @since 1.0
	 */
	<T> T unwrap(Class<T> servletClass);

	/**
	 * Create a {@link Marshaller}. The Marshaller obtained from this method
	 * should not be passed between threads.
	 * 
	 * @since 1.0
	 * @return The newly created Marshaller.
	 */
	Marshaller createMarshaller();

	/**
	 * Returns the mapping from paths to Handlers.
	 * 
	 * @return A Map with String-arrays as keys (which are paths) and handlers
	 *         as values.
	 * @since 1.0
	 */
	SortedMap<String[], Handler> getHandlers();

	/**
	 * Returns the loaded modules.
	 * 
	 * @return A Map of modules, with the name of the modules as keys.
	 * @since 1.0
	 */
	Map<String, Module> getLoadedModules();

	/**
	 * Returns the default TimeZone which is configured for this Weblet.
	 * 
	 * @return The default TimeZone for this Weblet or the current Locales
	 *         default, if the default TimeZone is not configured.
	 * @since 1.0
	 */
	TimeZone getDefaultTimeZone();

	/**
	 * Determines the default TimeZone for a Request. This may happen by looking
	 * up the current user, consulting the user database for the users preferred
	 * time zone, etc. It is also imaginable that a Weblet performs a geo
	 * location lookup in order to find the TimeZone for a given request.
	 * 
	 * @param request
	 *            The request for which a TimeZone needs to be found.
	 * @return The users preferred TimeZone or (if not available)
	 *         {@link #getDefaultTimeZone()}.
	 * @since 1.0
	 */
	TimeZone getTimeZoneForRequest(Request request);

	/**
	 * Returns an unmodifiable view on this Weblets configuration properties.
	 * 
	 * @return An unmodifiable properties view.
	 * @since 1.0
	 */
	UnmodifiableProperties getProperties();
}
