package net.scravy.hydrogen.compiler;

import java.util.Collections;
import java.util.List;

public class Block extends Container {

	Block() {

	}

	public List<Token> getDeclaration() {
		return Collections.unmodifiableList(declaration);
	}
}
