package net.scravy.hydrogen.compiler;

import java.io.File;
import java.io.IOException;

public class TokenTest1 {

	public static void main(String... args) throws IOException {
		Tokenizer1 tok = new Tokenizer1(new File("tokens.txt"));

		for (Token t : tok) {
			String stringRepresentation = t.toString().replace("\n", "\\n");
			System.out.printf("T %2d %2d %3d %3d %s: >>%s<<\n",
					t.line, t.getPos(), t.beginIndex, t.endIndex, t.type,
					stringRepresentation);
		}
	}

}
