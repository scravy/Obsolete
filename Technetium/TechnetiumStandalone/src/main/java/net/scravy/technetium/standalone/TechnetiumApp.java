package net.scravy.technetium.standalone;

import java.util.Properties;

import net.scravy.technetium.TechnetiumWeblet;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import com.google.common.base.Joiner;

/**
 * The standalone Technetium class. This class is capable of creating a Jetty
 * webserver and instantiating the Technetium Weblet inside.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class TechnetiumApp {

	final Properties properties;

	/**
	 * Creates a TechnetiumApp-object with the specified properties.
	 * 
	 * @param properties
	 *            A properties object (used for configuration of the Technetium
	 *            weblet).
	 */
	public TechnetiumApp(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Starts the webserver on the specified port and installs the Technetium
	 * weblet at the given path (called `mountpoint`).
	 * 
	 * @param port
	 *            The webserver port.
	 * @param mountpoint
	 *            The path for Technetium (like <code>"/"</code> or
	 *            <code>"/technetium/"</code>).
	 * @return An instance of the newly started webserver.
	 * @throws Exception
	 *             Starting the webserver may throw an exception. See
	 *             {@link Server#start()}.
	 */
	public Server startServer(int port, String mountpoint) throws Exception {
		mountpoint = '/' + mountpoint;
		mountpoint = Joiner.on('/').join(mountpoint.split("/+"));
		mountpoint = mountpoint + "/*";

		Server server = new Server(port);
		Context context = new Context(server, "/", Context.SESSIONS);

		TechnetiumWeblet servlet = new TechnetiumWeblet();
		ServletHolder servletHolder = new ServletHolder(servlet);
		servletHolder.setInitParameters(properties);
		context.addServlet(servletHolder, mountpoint);
		server.start();
		return server;
	}
}
