package net.scravy.hydrogen.test;

import java.io.File;
import java.io.IOException;

import net.scravy.hydrogen.compiler.*;
import net.scravy.hydrogen.compiler.Compiler.Tokens;
import net.scravy.hydrogen.compiler.Compiler.Warning;
import net.scravy.hydrogen.compiler.Compiler;

public class ParserTest1 {

	public static void main(String... args) throws IOException {

		// new BufferedReader(new InputStreamReader(System.in)).readLine();

		Compiler compiler = new Compiler();

		Tokens tokens = compiler.tokenize(new File("tokens.txt"));

		int errorCount = 0;
		for (Warning m : tokens.getMessages()) {
			if (m.getMessageType() == MessageType.ERROR) {
				errorCount++;
			}
			if (m.getMessageType() != MessageType.INFO) {
				System.err.println(m);
				System.err.println();
			}
		}

		if (errorCount > 0) {
			System.err.println("There were errors, aborting compile.");
			return;
		}

		Document doc = compiler.parse(tokens);

		doc.dump();
	}

}
