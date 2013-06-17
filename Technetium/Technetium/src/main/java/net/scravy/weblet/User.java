package net.scravy.weblet;

import java.util.Date;
import java.util.TimeZone;

import net.scravy.technetium.util.BCrypt;

/**
 * A User of a Weblet. Typically one of your domain classes will implement this
 * interface. If you need access to your own class in places where you do not
 * already have it, use {@link #unwrap(Class)} (like
 * <code>webletUser.unwrap(MyDomainPerson.class)</code>).
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public interface User {

	/**
	 * The encrypted password of the user. Which encryption scheme (if any) is
	 * left to the application.
	 * 
	 * @return The (possibly encrypted) password.
	 * @since 1.0
	 */
	String getUserPassword();

	/**
	 * Sets the password of the user. You should encrypt password, for example
	 * using {@link BCrypt}.
	 * 
	 * @param password
	 *            The (possibly encrypted) password.
	 * @since 1.0
	 */
	void setUserPassword(String password);

	/**
	 * Get the Instant at which the user logged most recently.
	 * 
	 * @return The Instant at which the user logged most recently.
	 */
	Date getLastTimeLoggedIn();

	/**
	 * Set the Instant at which the user logged most recently.
	 * 
	 * @param instant
	 *            The Instant at which the user logged most recently.
	 * @since 1.0
	 */
	void setLastTimeLoggedIn(Date instant);

	/**
	 * Unwrap a specific instance of a User.
	 * 
	 * @param clazz
	 *            The class to unwrap.
	 * @return This user as instance of the specified class, or null, if the
	 *         current instance does not implement the given interface nor
	 *         extends the given class.
	 * @since 1.0
	 */
	<T> T unwrap(Class<T> clazz);

	/**
	 * Get the TimeZone of the User. The TimeZone may have been configured by
	 * the User or determined by the Users location.
	 * 
	 * @return The Users TimeZone.
	 * @since 1.0
	 */
	TimeZone getUserTimeZone();
}
