package net.scravy.hydrogen.compiler;

import java.util.regex.Pattern;

class CustomTokenType implements TokenTypeInternal {

	private final String name;

	CustomTokenType(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean isCrucial() {
		return false;
	}

	@Override
	public boolean isUserDefined() {
		return true;
	}

	@Override
	public Message process(Tokenizer2 tokenizer, Token token) {
		return null;
	}

	@Override
	public Pattern getPattern() {
		return null;
	}

}
