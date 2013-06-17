package net.scravy.technetium;

import net.scravy.persistence.PersistenceUtil;
import net.scravy.technetium.util.EnhancedProperties;
import net.scravy.weblet.transform.CachingTransformerPool;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public abstract class SeleniumTest {

	private static Object lock = new Object();
	private static SeleniumServer seleniumServer;
	private static Selenium selenium;
	private static Server jettyServer;
	private static int depth = 0;

	protected Selenium selenium() {
		return selenium;
	}

	@SuppressWarnings("serial")
	@BeforeClass
	public static void setup() throws Exception {
		synchronized (lock) {
			BasicConfigurator.configure();
			Logger.getRootLogger().setLevel(Level.WARN);

			System.setProperty("eclipselink.logging.level", "WARNING");

			if (depth == 0) {
				int port = 4447;

				jettyServer = new Server(port);
				Context context = new Context(jettyServer, "/",
						Context.SESSIONS);

				TechnetiumWeblet servlet = new TechnetiumWeblet();
				ServletHolder servletHolder = new ServletHolder(servlet);
				servletHolder.setInitParameters(new EnhancedProperties() {
					{
						setProperty("javax.persistence.jdbc.driver",
								DatabaseTest.driver);
						setProperty("javax.persistence.jdbc.url",
								String.format(DatabaseTest.url, "jettydb"));
						setProperty("technetium.transformers.pool",
								CachingTransformerPool.class.getCanonicalName());
					}
				});
				context.addServlet(servletHolder, "/*");
				jettyServer.start();
				
				PersistenceUtil.createTables(servlet.db());
				DatabaseTest.initData(servlet.db());

				seleniumServer = new SeleniumServer();
				seleniumServer.start();

				selenium = new DefaultSelenium("localhost", 4444, "*firefox",
						"http://localhost:" + port + '/');
				selenium.start();
			}
			depth++;
		}
	}

	@AfterClass
	public static void teardown() throws Exception {
		synchronized (lock) {
			depth--;

			if (depth == 0) {
				selenium.stop();
				seleniumServer.stop();

				jettyServer.stop();
			}
		}
	}

}
