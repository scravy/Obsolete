package net.scravy.hydrogen.compiler;

import java.util.regex.Pattern;

enum MagicTokenType implements TokenTypeInternal {

	M_STRING, M_NUMBER, M_FILE, M_DIRECTORY, M_DATETIME, M_RULES_FILE;

	@Override
	public boolean isCrucial() {
		return true;
	}

	@Override
	public boolean isUserDefined() {
		return false;
	}

	@Override
	public Message process(Tokenizer2 tokenizer, Token token) {
		return null;
	}

	@Override
	public Pattern getPattern() {
		return null;
	}

	@Override
	public String getName() {
		return name();
	}
}
