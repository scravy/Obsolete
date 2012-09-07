package net.abusingjava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Main-Class of the AbusingJava-Library-JAR which displays some Version
 * Information about the Library.
 */
@Author("Julian Fleischer")
@Version("2012-01-04")
@Since("2011-08-30")
public class About {

	public static final String IDENTIFIER = "AbusingJava Library v" + AbusingJava.versionOf(AbusingJavaMeta.class);
	public static final String URL = "http://www.AbusingJava.net/Library";
	
	/**
	 * The Visual About Window, shown when
	 * <code>java.awt.GraphicsEnvironment.isHeadless()</code> is <i>false</i>.
	 */
	public static class VisualAbout implements Runnable {
		public static Dimension SIZE = new Dimension(450, 50);

		/**
		 * Displays a small {@link JFrame} with the AbusingJava-Version, which
		 * exits the application on close.
		 */
		@Override
		public void run() {
			JFrame $frame = new JFrame(IDENTIFIER);
			$frame.setSize(SIZE);
			$frame.setResizable(false);
			$frame.setAlwaysOnTop(true);
			$frame.setUndecorated(true);

			JPanel $panel = new JPanel();
			$panel.setLayout(null);
			$frame.setContentPane($panel);
			
			JLabel $label = new JLabel("<html>" + IDENTIFIER + "<br>" + URL, SwingConstants.CENTER);
			$panel.add($label);
			$label.setLocation(new Point(0, 0));
			$label.setSize(SIZE);
			$label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent $ev) {
					System.exit(0);
				}
			});

			$label.setForeground(Color.WHITE);
			$panel.setBackground(Color.BLACK);

			$panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

			$frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			$frame.setLocationRelativeTo(null);
			$frame.setVisible(true);
		}
	}

	/**
	 * The CLI About Message, shown when
	 * <code>java.awt.GraphicsEnvironment.isHeadless()</code> is <i>true</i>.
	 */
	public static class TerminalAbout implements Runnable {
		/**
		 * Displays the AbusingJava-Version using
		 * <code>System.out.println</code>.
		 */
		@Override
		public void run() {
			System.out.println("\n\t" + IDENTIFIER + "\n");
		}
	}

	/**
	 * Determines whether <code>java.awt.GraphicsEnvironment.isHeadless()</code>
	 * is true or false and runs {@link TerminalAbout} or {@link VisualAbout}
	 * accordingly.
	 */
	public static void main(final String... $args) {
		if (java.awt.GraphicsEnvironment.isHeadless()) {
			new TerminalAbout().run();
		} else {
			new VisualAbout().run();
		}
	}

}
