package net.scravy.technetium.misses_o;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * The Technetium Scheduler used to create schedules based on the data and
 * constraints in the database.
 * <p>
 * The Scheduler can be used as a component (simply create an instance) or in a
 * sepaate process. Communication is established using a Socket than, just make
 * it {@link #listen(int)} on a given port.
 * </p>
 * <p>
 * The actual network interface is implemented in {@link CommandInterface}.
 * </p>
 * <p>
 * This class offers a main method for directly creating a listening Scheduler.
 * </p>
 * <p>
 * The actual scheduling algorithms can be found in {@link Scheduler}. This
 * class merely acts as a controller to the schedulers implemented there.
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
public class MissesO {

	Thread listener = null;

	/**
	 * Default no-arg constructor.
	 */
	public MissesO() {

	}

	/**
	 * Spawns a new thread which listens on the given port for commands via TCP.
	 * The actual handling of a new client happens in {@link CommandInterface}.
	 * 
	 * @param port
	 *            The port.
	 * @throws IOException
	 *             If some kind of network error occurs.
	 */
	public void listen(final int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		socket.setSoTimeout(500);

		listener = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						Socket client = socket.accept();
						Thread handler = new Thread(
								new CommandInterface(MissesO.this, client));
						handler.start();
					} catch (SocketTimeoutException exc) {
						// do nothing
					} catch (IOException exc) {
						// report exception
					}
				}
			}
		});
		listener.start();
	}

	/**
	 * Stop listening (see {@link #listen(int)}.
	 */
	public void stopListening() {
		if (listener != null && listener.isAlive()) {
			listener.interrupt();
		}
	}

	/**
	 * Starts a listening Misses-O Scheduler on port 1337.
	 * 
	 * @param args
	 *            Command line arguments (none are used ATM).
	 * @throws Exception
	 *             Anything may happen.
	 */
	public static void main(String... args) throws Exception {
		new MissesO().listen(1337);
	}
}