package net.scravy.weblet;

/**
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public interface Session {

	/**
	 * Retrieve the user associated with this Session.
	 * 
	 * @return The User associated with this Session or null, if this is an
	 *         anonymous Session (i.e. the current user is not logged in).
	 */
	User getUser();

	/**
	 * Set the user associated with this Session
	 * 
	 * @param user
	 *            The user.
	 */
	void setUser(User user);

	<T> T get(String key, T def);

	<T> void set(String key, T value);

}
