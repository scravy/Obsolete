package net.scravy.hydrogen.compiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Container {

	final ArrayList<Token> declaration = new ArrayList<Token>();
	final ArrayList<Block> blocks = new ArrayList<Block>();

	Container() {

	}

	public List<Block> getBlocks() {
		return Collections.unmodifiableList(blocks);
	}

	void add(Token tok) {
		declaration.add(tok);
	}

	void add(Block block) {
		blocks.add(block);
	}

	public boolean isEmpty() {
		return blocks.isEmpty();
	}

}
