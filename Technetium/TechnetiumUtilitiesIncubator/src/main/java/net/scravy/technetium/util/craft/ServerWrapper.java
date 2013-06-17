package net.scravy.technetium.util.craft;

import java.io.*;

public class ServerWrapper {

	static class Printer implements Runnable {

		final BufferedReader reader;
		Exception exception = null;

		Printer(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		@Override
		public void run() {
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException exc) {
				exception = exc;
				Thread.currentThread().interrupt();
			}
		}
	}

	static class ShutdownHook implements Runnable {

		final Process p;

		ShutdownHook(Process p) {
			this.p = p;
		}

		@Override
		public void run() {
			try {
				Writer w = new OutputStreamWriter(p.getOutputStream());
				w.write("stop\n");
				w.flush();
				w.close();
			} catch (IOException exc) {
				Thread.currentThread().interrupt();
			}
		}

	}

	public static void main(String... args) throws
			IOException, InterruptedException {

		ProcessBuilder pb = new ProcessBuilder(
				"java",
				"-Xms2000M",
				"-Xmx2000M",
				"-jar",
				"/Users/scravy/Dropbox/Georg/MinecraftServer/minecraft_server.jar",
				"nogui");

		pb.directory(new File("/Users/scravy/Dropbox/Georg/MinecraftServer"));

		pb.redirectErrorStream(true);

		Process p = pb.start();

		Thread pout = new Thread(new Printer(p.getInputStream()));
		pout.setDaemon(true);
		pout.start();

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(p)));

		OutputStreamWriter writer = new OutputStreamWriter(p.getOutputStream());

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));
		String line = null;

		while ((line = reader.readLine()) != null) {
			if (line.equals("stop")) {
				writer.write("stop\n");
				writer.flush();
				break;
			}

			if (line.equals("explore")) {
				for (int x = -1000; x <= 1000; x += 100) {
					for (int z = -1000; z <= 1000; z += 100) {
						writer.write(String.format(
								"tp scravy %d %d %d\n", x, 100, z));
						writer.flush();
						Thread.sleep(3000);
					}
				}
			}
			writer.write(line);
			writer.write('\n');
			writer.flush();
		}

		p.waitFor();
	}
}
