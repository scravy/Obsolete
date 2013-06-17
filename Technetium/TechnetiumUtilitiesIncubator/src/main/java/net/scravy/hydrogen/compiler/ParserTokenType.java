package net.scravy.hydrogen.compiler;

public enum ParserTokenType implements TokenType {

	C_MOLECULE, C_MACRO_INVOCATION, C_BRACKETS,

	P_NUMBER, P_CHAR, P_STRING, P_BYTE_STRING, P_REGEX,
	P_NAME, P_DATE, P_XML, P_CONSTANT, P_TYPE, P_ANNOTATION,
	P_MEMBER, P_INFIX, P_PREFIX, P_SUFFIX;

	@Override
	public String getName() {
		return name();
	}
}
