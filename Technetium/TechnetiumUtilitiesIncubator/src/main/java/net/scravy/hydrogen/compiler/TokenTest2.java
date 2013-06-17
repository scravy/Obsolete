package net.scravy.hydrogen.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import net.scravy.hydrogen.compiler.Compiler.Warning;

public class TokenTest2 {

	public static void main(String... args) throws IOException {

		// new BufferedReader(new InputStreamReader(System.in)).readLine();

		// Long time = System.nanoTime();
		Tokenizer2 tok = new Tokenizer2(new Tokenizer1(new File("tokens.txt")));

		List<Token> tokens = tok.tokenize();
		// time = System.nanoTime() - time;
		// System.out.println(time / 1000 / 1000 + "ms");

		for (Warning m : tok.getMessages()) {
			System.err.println(m);
			System.err.println();
		}

		for (Entry<String, Object> e : tok.getProperties().entrySet()) {
			System.out.printf("%s := %s\n", e.getKey(), e.getValue());
		}

		TokenList.TokenLink link = tok.tokens.first;

		for (Token t : tokens) {
			String stringRepresentation = t.toString().replace("\n", "\\n");
			System.out.printf("T %2d %2d %3d %3d %s: >>%s<< (%s)\n",
					t.line, t.getPos(), t.beginIndex, t.endIndex, t.type,
					stringRepresentation,
					t.value == null
							? '-' : t.value.getClass().getSimpleName());
			link = link.next;
		}
	}
}
