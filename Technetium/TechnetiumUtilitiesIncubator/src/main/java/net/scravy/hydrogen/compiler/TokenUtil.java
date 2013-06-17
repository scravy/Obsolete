package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.TokenizerMessage.*;

import java.util.regex.Pattern;

class TokenUtil {

	@SuppressWarnings("serial")
	static class InvalidCharacterEscape extends Exception {
		final Message m;

		InvalidCharacterEscape(Message m) {
			this.m = m;
		}
	}

	private static int regexEscape(
			StringBuilder b, String string, char c, int i)
			throws InvalidCharacterEscape {
		switch (c) {
		case '+':
		case '-':
		case '*':
		case '?':
		case '$':
		case '^':
		case '\\':
		case '(':
		case ')':
		case '{':
		case '}':
		case ':':
			b.append('\\');
			b.append(c);
			break;
		default:
			i = unicodeEscape(b, string, c, i);
		}
		return i;
	}

	private static int asciiEscape(StringBuilder b, String string, char c, int i)
			throws InvalidCharacterEscape {
		switch (c) {
		case '\\':
		case '\'':
		case '"':
		case '`':
			b.append(c);
			break;
		case 't':
			b.append('\t');
			break;
		case 'r':
			b.append('\r');
			break;
		case 'n':
			b.append('\n');
			break;
		case 'f':
			b.append('\f');
			break;
		case 'b':
			b.append('\b');
			break;
		case 'v':
			throw new InvalidCharacterEscape(E_ILLEGAL_CHARACTER_ESCAPE_V);
		case 'x':
			try {
				int code = Integer.parseInt(string.substring(i + 1, i + 3), 16);
				b.append((char) code);
			} catch (Exception exc) {
				throw new InvalidCharacterEscape(
						W_INVALID_CHARACTER_ESCAPE_SEQUENCE_X);
			}
			i += 2;
			break;
		default:
			throw new InvalidCharacterEscape(W_UNKNOWN_CHARACTER_ESCAPE);
		}
		return i;
	}

	private static int unicodeEscape(StringBuilder b, String string, char c,
			int i)
			throws InvalidCharacterEscape {
		switch (c) {
		case 'u':
			try {
				int code = Integer.parseInt(string.substring(i + 1, i + 5), 16);
				b.append((char) code);
			} catch (Exception exc) {
				throw new InvalidCharacterEscape(
						W_INVALID_CHARACTER_ESCAPE_SEQUENCE_U);
			}
			i += 4;
			break;
		case 'X':
			try {
				int code = Integer.parseInt(string.substring(i + 1, i + 7), 16);
				b.append((char) code);
			} catch (Exception exc) {
				throw new InvalidCharacterEscape(
						W_INVALID_CHARACTER_ESCAPE_SEQUENCE_CX);
			}
			i += 6;
			break;
		case 'U':
			try {
				int code = Integer.parseInt(string.substring(i + 1, i + 9), 16);
				b.append((char) code);
			} catch (Exception exc) {
				throw new InvalidCharacterEscape(
						W_INVALID_CHARACTER_ESCAPE_SEQUENCE_CU);
			}
			i += 8;
			break;
		default:
			i = asciiEscape(b, string, c, i);
		}
		return i;
	}

	// DONE.
	static Message parseQuotString(Token token) {
		StringBuilder b = new StringBuilder();
		for (int i = token.beginIndex + 1; i < token.endIndex - 1; i++) {
			char c = token.string.charAt(i);
			if (c == '\\') {
				i++;
				c = token.string.charAt(i);
				try {
					i = unicodeEscape(b, token.string, c, i);
				} catch (InvalidCharacterEscape exc) {
					return exc.m;
				}
			} else if (c != '\n') {
				b.append(c);
			}
		}
		token.setValue(b.toString());
		return null;
	}

	// TODO: Indentation.
	static Message parseTripleQuotString(Token token, int tabsize) {
		StringBuilder b = new StringBuilder();
		for (int i = token.beginIndex + 3; i < token.endIndex - 3; i++) {
			char c = token.string.charAt(i);
			if (c == '\\') {
				i++;
				c = token.string.charAt(i);
				try {
					i = unicodeEscape(b, token.string, c, i);
				} catch (InvalidCharacterEscape exc) {
					return exc.m;
				}
			} else if (c != '\n') {
				b.append(c);
			}
		}
		token.setValue(b.toString());
		return null;
	}

	// DONE.
	static Message parseAposString(Token token) {
		char c = token.string.charAt(token.beginIndex + 1);
		if (c == '\\') {
			StringBuilder b = new StringBuilder();
			try {
				int i = unicodeEscape(b, token.string,
						token.string.charAt(token.beginIndex + 2),
						token.beginIndex + 2);
				token.setValue(b.charAt(0));
				if (i + 2 != token.endIndex) {
					return W_CHARACTER_MAY_ONLY_BE_A_SINGLE_CHARACTER;
				}
			} catch (InvalidCharacterEscape exc) {
				token.setValue(c);
				return exc.m;
			}
		} else {
			token.setValue(c);
			if (token.beginIndex + 3 != token.endIndex) {
				return W_CHARACTER_MAY_ONLY_BE_A_SINGLE_CHARACTER;
			}
		}
		return null;
	}

	// DONE.
	static Message parseDoubleAposString(Token token) {
		StringBuilder b = new StringBuilder();
		for (int i = token.beginIndex + 2; i < token.endIndex - 2; i++) {
			char c = token.string.charAt(i);
			if (c > 127) {
				return E_NON_ASCII_CHARACTER_IN_BINARY_STRING;
			}
			if (c == '\\') {
				i++;
				c = token.string.charAt(i);
				try {
					i = asciiEscape(b, token.string, c, i);
				} catch (InvalidCharacterEscape exc) {
					return exc.m;
				}
			} else if (c != '\n') {
				b.append(c);
			}
		}
		token.setValue(b.toString());
		return null;
	}

	// TODO: Rework regular expressions.
	static Message parseBacktickString(Token token) {
		StringBuilder b = new StringBuilder();
		for (int i = token.beginIndex + 1; i < token.endIndex - 1; i++) {
			char c = token.string.charAt(i);
			if (c == '\\') {
				i++;
				c = token.string.charAt(i);
				try {
					i = unicodeEscape(b, token.string, c, i);
				} catch (InvalidCharacterEscape exc) {
					return exc.m;
				}
			} else if (c != '\n') {
				b.append(c);
			}
		}
		Pattern pattern = Pattern.compile(b.toString());
		token.setValue(pattern);
		return null;
	}

	// TODO: Rework regular expressions.
	static Message parseDoubleBacktickString(Token token) {
		StringBuilder b = new StringBuilder();
		for (int i = token.beginIndex + 2; i < token.endIndex - 2; i++) {
			char c = token.string.charAt(i);
			if (c == '\\') {
				i++;
				c = token.string.charAt(i);
				try {
					i = regexEscape(b, token.string, c, i);
				} catch (InvalidCharacterEscape exc) {
					return exc.m;
				}
			} else if (c != '\n') {
				b.append(c);
			}
		}
		Pattern pattern = Pattern.compile(b.toString());
		token.setValue(pattern);
		return null;
	}

}
