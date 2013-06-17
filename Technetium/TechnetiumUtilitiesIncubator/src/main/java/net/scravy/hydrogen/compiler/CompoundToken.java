package net.scravy.hydrogen.compiler;

import java.util.ArrayList;

class CompoundToken extends Token {

	final ArrayList<Token> tokens = new ArrayList<Token>();

	CompoundToken(Token tok, TokenType type) {
		super(tok.string, type, tok.source,
				tok.line, tok.beginIndex, tok.endIndex);
	}

	void add(Token tok) {
		tokens.add(tok);
	}
}