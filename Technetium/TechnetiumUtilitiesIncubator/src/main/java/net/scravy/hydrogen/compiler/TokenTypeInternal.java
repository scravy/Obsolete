package net.scravy.hydrogen.compiler;

import java.util.regex.Pattern;


interface TokenTypeInternal extends TokenType {

	boolean isCrucial();

	boolean isUserDefined();

	Pattern getPattern();

	Message process(Tokenizer2 tokenizer, Token token);
}
