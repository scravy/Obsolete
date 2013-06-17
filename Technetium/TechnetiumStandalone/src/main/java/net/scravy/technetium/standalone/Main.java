package net.scravy.technetium.standalone;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;

import net.scravy.technetium.util.EnhancedProperties;
import net.scravy.technetium.util.TypeUtil;
import net.scravy.technetium.util.value.ArrayUtil;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.slf4j.LoggerFactory;

/**
 * The Main entry point for the Technetium Standalone JAR (Executable).
 * 
 * @author Julian Fleischer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Main {

	private final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(Main.class);

	private final static Properties properties = new EnhancedProperties() {
		{
			setProperty("javax.persistence.jdbc.driver",
					"org.apache.derby.jdbc.EmbeddedDriver");
			setProperty("javax.persistence.jdbc.url",
					"jdbc:derby:technetium;create=true");
			setProperty("javax.persistence.jdbc.user", "technetium");
			setProperty("javax.persistence.jdbc.password",
					"superficiallySecurePassword");

			setProperty("technetium.transformers.pool",
					"net.scravy.weblet.transform.CachingTransformerPool");
			setProperty("technetium.scriptloader.precompile", "true");
			setProperty("technetium.standalone.loglevel", "WARN");
		}
	};

	/**
	 * The main method.
	 * 
	 * @param args
	 *            Command line arguments.
	 * @throws Exception
	 *             Anything may happen.
	 */
	public static void main(String... args) throws Exception {
		if (ArrayUtil.indexOf(args, "--help") >= 0
				|| ArrayUtil.indexOf(args, "-h") >= 0) {
			System.out
					.println("Technetium Standalone Distribution"
							+ "\n"
							+ "If a graphics environment is available, a GUI will be started.\n"
							+ "If not, the CLI version will come up, which automatically starts the\n"
							+ "standalone webserver based on the configuration in `technetium.properties`.\n"
							+ "\n"
							+ "Use --nogui to force the CLI version.\n"
							+ "--port specifies the port to run the Technetium Webserver.\n");
			return;
		}

		int port = 8080;
		int portArg = ArrayUtil.indexOf(args, "--port");
		if (portArg >= 0) {
			port = TypeUtil.convert(args[portArg + 1], port);
		}
		boolean headless = GraphicsEnvironment.isHeadless()
				|| ArrayUtil.indexOf(args, "--nogui") >= 0;

		BasicConfigurator.configure();

		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
		Logger.getLogger("org.jboss").setLevel(Level.WARN);
		Logger.getLogger("org.mortbay").setLevel(Level.WARN);

		System.setProperty("eclipselink.logging.level", "WARNING");

		if (headless) {
			properties.load(Main.class
					.getResourceAsStream("nogui.defaults"));
		}

		File propertiesFile = new File("technetium.properties");
		if (propertiesFile.exists()) {
			if (propertiesFile.canRead()) {
				properties.load(
						new FileInputStream("technetium.properties"));
			} else {
				logger.warn("technetium.properties is not readable");
			}
			if (propertiesFile.canWrite()) {
				properties.store(
						new FileOutputStream("technetium.properties"), "");
			}
		} else {
			try {
				properties.store(
						new FileOutputStream("technetium.properties"), "");
			} catch (Exception exc) {
				logger.warn(
						"Could not write properties file “technetium.propertie”",
						exc);
			}
		}

		String logLevel = properties
				.getProperty("technetium.standalone.loglevel");
		Logger.getLogger("net.scravy").setLevel(Level.toLevel(logLevel));

		final TechnetiumApp app = new TechnetiumApp(properties);

		if (headless) {
			Server server = app.startServer(port, "/");
			try {
				server.join();
			} catch (InterruptedException exc) {
				logger.info("Server interrupted.");
			}
		} else {
			MainWindow.setNimbusLookNFeel();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					final MainWindow frame = new MainWindow(app);
					frame.setVisible(true);
					frame.stealStdStreams();

					System.out
							.println("Technetium Standalone GUI started up.\n"
									+ "Iff you don't like the GUI you sould try --nogui.\n"
									+ "See --help for details.");
				}
			});
		}
	}

}
