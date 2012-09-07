/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava.swing;

import java.awt.Dimension;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.abusingjava.Author;
import net.abusingjava.Box;
import net.abusingjava.NotGonnaHappenException;
import net.abusingjava.Since;
import net.abusingjava.Version;
import net.abusingjava.swing.magic.Panel;
import net.abusingjava.swing.magic.Window;
import net.abusingjava.xml.AbusingXML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains usefull static methods for handling Swing.
 * <p>
 * AbusingSwing eases handling Swing and SwingX components using a custom XML
 * dialect.
 * 
 * 
 */
@Author("Julian Fleischer")
@Since("2011-06-21")
@Version("2011-10-16")
final public class AbusingSwing {

	private static final Logger $logger = LoggerFactory.getLogger(AbusingSwing.class);

	private AbusingSwing() {
	}

	/**
	 * Sets the look and feel with the specified name.
	 */
	public static void setLookAndFeel(final String $name) {
		try {
			for (LookAndFeelInfo $info : UIManager.getInstalledLookAndFeels()) {
				if ($name.equals($info.getName())) {
					UIManager.setLookAndFeel($info.getClassName());
					break;
				}
			}
		} catch (Exception $exc) {
			$logger.warn("Could not set Look and feel “" + $name + "”.", $exc);
		}
	}

	/**
	 * Sets the Nimbus Look And Feel which is available since Java 6 Update 10.
	 */
	public static void setNimbusLookAndFeel() {
		setLookAndFeel("Nimbus");
	}

	/**
	 * Show a $panel in a temporary frame.
	 */
	public static JFrame showPanel(final JPanel $panel) {
		return showPanel($panel, true);
	}

	/**
	 * Show a $panel in a temporary frame. If $close, than closing the frame
	 * will also shut down the application.
	 */
	public static JFrame showPanel(final JPanel $panel, final boolean $close) {
		final JFrame $frame = new JFrame() {
			private static final long serialVersionUID = 4965692958830497098L;
			{
				if ($close) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				setContentPane($panel);
				pack();
				setMinimumSize($panel.getMinimumSize());
			}
		};
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				$frame.setVisible(true);
			}
		});
		return $frame;
	}

	/**
	 * Show a $panel with size $width and $height.
	 */
	public static JFrame showPanel(final JPanel $panel, final int $width, final int $height) {
		return showPanel($panel, true, $width, $height);
	}

	/**
	 * Show a MagicPanel which is defined in the named $resource in a temporary
	 * JFrame.
	 * <p>
	 * The resource should be located in the same directory as the calling
	 * class.
	 */
	public static JFrame showPanel(final JPanel $panel, final boolean $close, final int $width, final int $height) {
		final JFrame $frame = new JFrame() {
			private static final long serialVersionUID = -5067180055790051146L;
			{
				if ($close) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				setContentPane(new JScrollPane($panel));
				pack();
				setSize($width, $height);
			}
		};
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				$frame.setVisible(true);
			}
		});
		return $frame;
	}

	/**
	 * Show a MagicWindow whic is defined in the resource named $resource.
	 * <p>
	 * The resource should be located in the same directory as the calling
	 * class.
	 */
	public static MagicWindow showWindow(final String $resource) {
		MagicWindow $window = makeWindow($resource);
		$window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		$window.setVisible(true);
		return $window;
	}

	/**
	 * Show a MagicPanel which is defined in $resource with $width and $height.
	 */
	public static MagicPanel showPanel(final String $resource, final int $width, final int $height) {
		MagicPanel $panel = makePanel($resource);
		JFrame $window = new JFrame();
		$window.setContentPane($panel);
		$window.setMinimumSize(new Dimension($width, $height));
		$window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		$window.setVisible(true);
		return $panel;
	}

	/**
	 * Create a MagicPanel from the given $resource.
	 */
	public static MagicPanel makePanel(final String $resource) {
		int $i = 0;
		String $className;
		do {
			$className = new Exception().getStackTrace()[$i++].getClassName();
		} while ($className.equals(AbusingSwing.class.getCanonicalName()));
		try {
			InputStream $stream = Class.forName($className).getResourceAsStream($resource);
			return makePanel($stream);
		} catch (ClassNotFoundException $exc) {
			throw new NotGonnaHappenException($exc);
		}
	}

	/**
	 * Make a MagicWindow from the given $resource.
	 */
	public static MagicWindow makeWindow(final String $resource) {
		int $i = 0;
		String $className;
		do {
			$className = new Exception().getStackTrace()[$i++].getClassName();
		} while ($className.equals(AbusingSwing.class.getCanonicalName()));
		try {
			InputStream $stream = Class.forName($className).getResourceAsStream($resource);
			return makeWindow($stream);
		} catch (ClassNotFoundException $exc) {
			throw new NotGonnaHappenException($exc);
		}
	}

	/**
	 * Make a MagicWindow from the given $stream.
	 */
	public static MagicWindow makeWindow(final InputStream $stream) {
		Window $window = AbusingXML.loadXML($stream, Window.class);
		return new MagicWindow($window);
	}

	/**
	 * Make a MagicPanel from the given $stream.
	 */
	public static MagicPanel makePanel(final InputStream $stream) {
		Panel $panel = AbusingXML.loadXML($stream, Panel.class);
		return new MagicPanel($panel);
	}

	/**
	 * Invokes the $runnable using {@link SwingUtilities#invokeLater(Runnable)},
	 * iff the current thread is the event dispatch thread.
	 */
	public static void invokeLater(final Runnable $runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			$runnable.run();
		} else {
			SwingUtilities.invokeLater($runnable);
		}
	}

	/**
	 * Invokes the $runnable using
	 * {@link SwingUtilities#invokeAndWait(Runnable)}, iff the current thread is
	 * the event dispatch thread.
	 */
	public static void invokeAndWait(final Runnable $runnable) {
		try {
			if (SwingUtilities.isEventDispatchThread()) {
				$runnable.run();
			} else {
				SwingUtilities.invokeAndWait($runnable);
			}
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}

	/**
	 * 
	 */
	public static Object get(final Object $object, final String $methodName) {
		try {
			final Method $m = $object.getClass().getMethod($methodName);
			final Box<Object> $b = new Box<Object>();
			if (SwingUtilities.isEventDispatchThread()) {
				return $m.invoke($object);
			}
			invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						$b.set($m.invoke($object));
					} catch (Exception $exc) {
						throw new RuntimeException($exc);
					}
				}
			});
			return $b.get();
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getT(final Object $object, final String $methodName) {
		try {
			final Method $m = $object.getClass().getMethod($methodName);
			final Box<T> $b = new Box<T>();
			if (SwingUtilities.isEventDispatchThread()) {
				return (T) $m.invoke($object);
			}
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						$b.set((T) $m.invoke($object));
					} catch (Exception $exc) {
						throw new RuntimeException($exc);
					}
				}
			});
			return $b.get();
		} catch (Exception $exc) {
			throw new RuntimeException($exc);
		}
	}

}
