package net.scravy.technetium.standalone;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The main window.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	final MainPanel panel;

	MainWindow(TechnetiumApp app) {
		super("Technetium");

		panel = new MainPanel(app);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);

		setMinimumSize(new Dimension(700, 400));

		pack();
		setLocationRelativeTo(null);
	}

	void stealStdStreams() {
		try {
			final PipedOutputStream out = new PipedOutputStream();
			final PipedInputStream in = new PipedInputStream(out);
			final BufferedReader lineReader = new BufferedReader(
					new InputStreamReader(in));

			new Thread(new Runnable() {
				@Override
				public void run() {
					String line;
					try {
						while ((line = lineReader.readLine()) != null) {
							panel.console.append(line + '\n');
						}
					} catch (IOException exc) {

					}
				}
			}).start();

			System.setErr(new PrintStream(out));
			System.setOut(new PrintStream(out));
		} catch (Exception exc) {

		}
	}

	static void setNimbusLookNFeel() {
		try {
			for (LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("nimbus".equalsIgnoreCase(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception $exc) {
		}
	}
}
