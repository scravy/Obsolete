package net.abusingjava;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Author("Julian Fleischer")
@Version("2011-01-04")
@Since("2011-01-04")
public class SystemDefaults {

	private static Desktop $desktop = null;
	private static Logger $logger = LoggerFactory.getLogger(SystemDefaults.class);

	static {
		if (Desktop.isDesktopSupported()) {
			try {
				$desktop = Desktop.getDesktop();
			} catch (Exception $exc) {
				$logger.warn("Desktop is not supported.", $exc);
			}
		}
	}

	/**
	 * Browses the specified uris using the System Browser.
	 * <p>
	 * In case of an error, this function issues the exception and a message of
	 * level INFO to its logger.
	 * 
	 * @param $uris
	 *            A list of URIs. The strings are passed to
	 *            {@link URI#create(String)}.
	 * @return true if all URIs were successfully opened, false if some kind of
	 *         error occurred.
	 */
	public static boolean browse(final String... $uris) {
		if (!$desktop.isSupported(Desktop.Action.BROWSE)) {
			$logger.warn("Desktop.Action.BROWSE is not supported.");
			return false;
		}
		try {
			for (String $uri : $uris) {
				$desktop.browse(URI.create($uri));
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not browse uri(s): %s.", Arrays.toString($uris)), $exc);
			return false;
		}
	}

	/**
	 * Browses the specified uris using the System Browser.
	 * <p>
	 * In case of an error, this function issues the exception and a message of
	 * level INFO to its logger.
	 * 
	 * @param $uris
	 *            A list of URIs.
	 * @return true if all URIs were successfully opened, false if some kind of
	 *         error occurred.
	 */
	public static boolean browse(final URI... $uris) {
		if ($uris == null) {
			$logger.warn("Passing zero to browse(URI...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.BROWSE)) {
			$logger.warn("Desktop.Action.BROWSE is not supported.");
			return false;
		}
		try {
			for (URI $uri : $uris) {
				$desktop.browse($uri);
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not browse uri(s): %s.", Arrays.toString($uris)), $exc);
			return false;
		}
	}

	/**
	 * Opens the system mail client.
	 * <p>
	 * In case of an error, this function issues the exception and a message of
	 * level INFO to its logger.
	 * 
	 * @param $emailAddresses
	 *            A list of URIs. The strings are passed to
	 *            {@link URI#create(String)} like so
	 *            <code>URI.create("mailto:" + $address)</code>. If this list is
	 *            empty or null, the Mail client is opened with no target.
	 * @return true if all URIs were successfully opened, false if some kind of
	 *         error occurred.
	 */
	public static boolean mail(final String... $emailAddresses) {
		if (!$desktop.isSupported(Desktop.Action.BROWSE)) {
			$logger.warn("Desktop.Action.BROWSE is not supported.");
			return false;
		}
		try {
			if (($emailAddresses == null) || ($emailAddresses.length == 0)) {
				$desktop.mail();
				return true;
			}
			for (String $address : $emailAddresses) {
				$desktop.mail(URI.create("mailto:" + $address));
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not mail to adresses(s): %s.", Arrays.toString($emailAddresses)), $exc);
			return false;
		}
	}

	/**
	 * Opens the system mail client. Use this method for sophisticated use of
	 * mailto-Addresses, like
	 * <code>mail(new URI("mailto:example@example.org?subject=Subject")</code>.
	 * <p>
	 * In case of an error, this function issues the exception and a message of
	 * level INFO to its logger.
	 * 
	 * @param $uris
	 *            A list of mailto-URIs.
	 * @return true if all URIs were successfully opened, false if some kind of
	 *         error occurred.
	 */
	public static boolean mail(final URI... $uris) {
		if (!$desktop.isSupported(Desktop.Action.BROWSE)) {
			$logger.warn("Desktop.Action.BROWSE is not supported.");
			return false;
		}
		try {
			if (($uris == null) || ($uris.length == 0)) {
				$desktop.mail();
				return true;
			}
			for (URI $uri : $uris) {
				$desktop.mail($uri);
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not mail to uri(s): %s.", Arrays.toString($uris)), $exc);
			return false;
		}
	}

	/**
	 * Opens the specified files. This method will not hang or wait for the
	 * opened application to exit.
	 * 
	 * @param $files
	 *            The files to open as list of Strings. The paths need to be
	 *            paths on your Systems, URIs are not allowed.
	 * @return true if all files were successfully opened, false if not.
	 */
	public static boolean open(final String... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to open(String...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.OPEN)) {
			$logger.warn("Desktop.Action.OPEN is not supported.");
			return false;
		}
		try {
			for (String $file : $files) {
				$desktop.open(new File($file));
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not open file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}

	/**
	 * Opens the specified files. This method will not hang or wait for the
	 * opened application to exit.
	 * 
	 * @param $files
	 *            The files to open as list of {@link File} File.
	 * @return true if all files were successfully opened, false if not.
	 */
	public static boolean open(final File... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to open(File...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.OPEN)) {
			$logger.warn("Desktop.Action.OPEN is not supported.");
			return false;
		}
		try {
			for (File $file : $files) {
				$desktop.open($file);
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not open file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}

	/**
	 * Opens the specified files for editing. This method will not hang or wait for the
	 * opened application to exit.
	 * 
	 * @param $files
	 *            The files to open as list of Strings. The paths need to be
	 *            paths on your Systems, URIs are not allowed.
	 * @return true if all files were successfully opened, false if not.
	 */
	public static boolean edit(final String... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to edit(String...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.EDIT)) {
			$logger.warn("Desktop.Action.EDIT is not supported.");
			return false;
		}
		try {
			for (String $file : $files) {
				$desktop.edit(new File($file));
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not edit file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}

	/**
	 * Opens the specified files for editing. This method will not hang or wait for the
	 * opened application to exit.
	 * 
	 * @param $files
	 *            The files to open as list of {@link File} File.
	 * @return true if all files were successfully opened, false if not.
	 */
	public static boolean edit(final File... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to browse(File...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.EDIT)) {
			$logger.warn("Desktop.Action.EDIT is not supported.");
			return false;
		}
		try {
			for (File $file : $files) {
				$desktop.edit($file);
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not edit file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}
	public static boolean print(final File... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to print(File...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.PRINT)) {
			$logger.warn("Desktop.Action.PRINT is not supported.");
			return false;
		}
		try {
			for (File $file : $files) {
				$desktop.print($file);
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not print file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}

	public static boolean print(final String... $files) {
		if ($files == null) {
			$logger.warn("Passing zero to print(String...)");
			return false;
		}
		if (!$desktop.isSupported(Desktop.Action.PRINT)) {
			$logger.warn("Desktop.Action.PRINT is not supported.");
			return false;
		}
		try {
			for (String $file : $files) {
				$desktop.print(new File($file));
			}
			return true;
		} catch (Exception $exc) {
			$logger.info(String.format("Could not print file(s): %s.", Arrays.toString($files)), $exc);
			return false;
		}
	}
}
