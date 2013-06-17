package net.scravy.hydrogen.compiler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Document extends Container implements Iterable<Block> {

	private final Map<String, Object> properties;

	Document(Map<String, Object> properties) {
		this.properties = properties;
	}

	private static void showCompound(CompoundToken tok) {
		System.out.print(tok.type);
		System.out.print("( ");
		showTokens(tok.tokens);
		System.out.print(')');
	}

	private static void showTokens(List<Token> tokens) {
		for (Token tok : tokens) {
			if (tok instanceof CompoundToken) {
				showCompound((CompoundToken) tok);
			} else if (tok.type == BasicTokenType.T_XML) {
				System.out.print("<<XML>>");
			} else {
				System.out.print(tok.getDeclaredValue());
			}

			System.out.print(' ');
		}
	}

	private static void showBlock(Container block, String indent) {

		System.out.print(indent);
		showTokens(block.declaration);
		if (block.blocks.isEmpty()) {
			System.out.println(";");
		} else {
			System.out.println("{");

			for (Container b : block.blocks) {
				showBlock(b, indent + "  ");
			}

			System.out.print(indent);
			System.out.println("}");
		}
	}

	public void dump() {
		showBlock(this, "");
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	@Override
	public Iterator<Block> iterator() {
		return blocks.iterator();
	}

}