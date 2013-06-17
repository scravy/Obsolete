package net.scravy.technetium.misses_o;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles a TCP Connection to {@link MissesO}.
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class CommandInterface implements Runnable {

	private final Socket client;
	private final MissesO parent;

	CommandInterface(MissesO parent, Socket client) {
		this.client = client;
		this.parent = parent;
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(client.getInputStream(), "UTF-8"),
					100);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream(), "UTF-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equalsIgnoreCase("hello")) {
					writer.println("hi");
					writer.flush();
				} else if (line.equalsIgnoreCase("shutdown")) {
					client.close();
					parent.stopListening();
					return;
				}
			}
			writer.println("Goodbye.");
			writer.flush();
			client.close();
		} catch (IOException exc) {
			Thread.currentThread().interrupt();
		}
	}

}