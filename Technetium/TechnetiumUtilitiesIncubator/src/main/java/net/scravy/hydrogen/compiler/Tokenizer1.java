package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.BasicTokenType.*;
import static net.scravy.hydrogen.compiler.Tokenizer1.State.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import net.scravy.technetium.util.IOUtil;
import net.scravy.technetium.util.RuntimeIOException;
import net.scravy.technetium.util.iterator.GenericIterator;

class Tokenizer1 implements Iterable<Token> {

	public static enum State {
		N, WS, NL, C, CM,
		Q, QQ, QS, Q3S, Q3Q, Q3QQ,
		A, AA, AAA, AS,
		B, BB, BBB, BS,
		LP, RP, LB, RB, LC, RC,

		X, XN, XT,
		XA, XAQ, XAA, XAS,
		XO, XO2, XC, XC2,
		XCA, XCAQ, XCAA, XCS
	}

	private final String string;
	private final File source;

	Tokenizer1(String string) {
		this.string = string;
		this.source = null;
	}

	Tokenizer1(File file) throws IOException {
		try {
			this.string = IOUtil.getContents(file);
		} catch (RuntimeIOException exc) {
			throw exc.getCause();
		}
		this.source = file.getCanonicalFile();
	}

	public File getSource() {
		return source;
	}

	public File getSourceDirectory() {
		if (source == null) {
			try {
				return new File(".").getCanonicalFile();
			} catch (IOException exc) {
				return new File(System.getProperty("user.dir"));
			}
		}
		return source.getParentFile();
	}

	@Override
	public Iterator<Token> iterator() {
		return new GenericIterator<Token>() {

			private State state = N;
			private int index = 0;
			private int start = 0;
			private int startLine = 1;
			private int line = 1;

			private Token t = null;
			private boolean dontCount = false;

			private Deque<String> tags = new ArrayDeque<String>();
			private StringBuilder tagName = new StringBuilder();

			private void token(TokenTypeInternal type, int endIndex) {
				if (endIndex > start) {
					if (type == T_NEW_LINE) {
						startLine--;
					}
					t = new Token(string, type, source, startLine, start,
							endIndex);
					start = endIndex;
					startLine = line;
				}
			}

			private void token(TokenTypeInternal type) {
				if (index > start) {
					if (type == T_NEW_LINE) {
						startLine--;
					}
					t = new Token(string, type, source, startLine, start, index);
					start = index;
					startLine = line;
				}
			}

			@Override
			protected Token nextElement() {
				t = null;

				for (; index < string.length(); index++) {
					if (t != null) {
						return t;
					}

					char c = string.charAt(index);

					if (c == '\r') {
						continue;
					}

					if (c == '\n' && !dontCount) {
						line++;
					}
					dontCount = false;

					currentStep: for (;;) {
						if (t != null) {
							dontCount = true;
							return t;
						}

						switch (state) {
						case N:
							switch (c) {
							case '<':
								token(T_UNKNOWN);
								state = X;
								break;
							case '(':
								token(T_UNKNOWN);
								state = LP;
								break;
							case ')':
								token(T_UNKNOWN);
								state = RP;
								break;
							case '[':
								token(T_UNKNOWN);
								state = LB;
								break;
							case ']':
								token(T_UNKNOWN);
								state = RB;
								break;
							case '{':
								token(T_UNKNOWN);
								state = LC;
								break;
							case '}':
								token(T_UNKNOWN);
								state = RC;
								break;
							case '"':
								token(T_UNKNOWN);
								state = Q;
								break;
							case '\'':
								token(T_UNKNOWN);
								state = A;
								break;
							case '`':
								token(T_UNKNOWN);
								state = B;
								break;
							case ';':
								token(T_UNKNOWN);
								state = C;
								break;
							case ',':
								token(T_UNKNOWN);
								state = CM;
								break;
							case ' ':
							case '\t':
								token(T_UNKNOWN);
								state = WS;
								break;
							case '\n':
								token(T_UNKNOWN);
								state = NL;
								break;
							}
							break;
						case WS:
							switch (c) {
							case ' ':
							case '\t':
								break;
							default:
								token(T_WHITE_SPACE);
								state = N;
								continue currentStep;
							}
							break;
						case NL:
							token(T_NEW_LINE);
							state = N;
							continue currentStep;
						case C:
							switch (c) {
							case '\n':
								token(T_COMMENT, index + 1);
								state = N;
								break;
							}
							break;
						case CM:
							token(T_COMMA);
							state = N;
							continue currentStep;
						case LP:
							token(T_OPENING_PARENTHESIS);
							state = N;
							continue currentStep;
						case RP:
							token(T_CLOSING_PARENTHESIS);
							state = N;
							continue currentStep;
						case LB:
							token(T_OPENING_BRACKET);
							state = N;
							continue currentStep;
						case RB:
							token(T_CLOSING_BRACKET);
							state = N;
							continue currentStep;
						case LC:
							token(T_OPENING_CURLY_BRACE);
							state = N;
							continue currentStep;
						case RC:
							token(T_CLOSING_CURLY_BRACE);
							state = N;
							continue currentStep;
						case Q:
							switch (c) {
							case '"':
								state = QQ;
								break;
							case '\\':
								state = QS;
								index++;
								break;
							default:
								state = QS;
								break;
							}
							break;
						case QS:
							switch (c) {
							case '"':
								state = N;
								token(T_QUOT_STRING, index + 1);
								break;
							case '\\':
								index++;
								break;
							}
							break;
						case QQ:
							switch (c) {
							case '"':
								state = Q3S;
								break;
							default:
								token(T_QUOT_STRING, index);
								continue currentStep;
							}
							break;
						case Q3S:
							switch (c) {
							case '"':
								state = Q3Q;
								break;
							case '\\':
								index++;
								break;
							}
							break;
						case Q3Q:
							switch (c) {
							case '"':
								state = Q3QQ;
								break;
							case '\\':
								state = Q3S;
								index++;
								break;
							default:
								state = Q3S;
								break;
							}
							break;
						case Q3QQ:
							switch (c) {
							case '"':
								token(T_TRIPLE_QUOT_STRING, index + 1);
								state = N;
								break;
							case '\\':
								state = Q3S;
								index++;
								break;
							default:
								state = Q3S;
								break;
							}
							break;
						case A:
							switch (c) {
							case '\'':
								state = AA;
								break;
							default:
								state = AS;
								continue currentStep;
							}
						case AA:
							switch (c) {
							case '\'':
								state = AAA;
								break;
							case '\\':
								index++;
								break;
							}
							break;
						case AAA:
							switch (c) {
							case '\'':
								token(T_DOUBLE_APOS_STRING, index + 1);
								state = N;
								break;
							default:
								state = AA;
								continue currentStep;
							}
							break;
						case AS:
							switch (c) {
							case '\'':
								token(T_APOS_STRING, index + 1);
								state = N;
								break;
							case '\\':
								index++;
								break;
							}
							break;
						case B:
							switch (c) {
							case '`':
								state = BB;
								break;
							default:
								state = BS;
								continue currentStep;
							}
						case BB:
							switch (c) {
							case '`':
								state = BBB;
								break;
							case '\\':
								index++;
								break;
							}
							break;
						case BBB:
							switch (c) {
							case '`':
								token(T_DOUBLE_BACKTICK_STRING, index + 1);
								state = N;
								break;
							default:
								state = BB;
								continue currentStep;
							}
							break;
						case BS:
							switch (c) {
							case '`':
								token(T_BACKTICK_STRING, index + 1);
								state = N;
								break;
							case '\\':
								index++;
								break;
							}
							break;

						case X:
							if (Character.isLetter(c)) {
								state = XN;
								tagName.append(c);
							} else {
								state = N;
								continue currentStep;
							}
							break;
						case XN:
							if (Character.isLetterOrDigit(c)
									|| c == '.'
									|| c == '-'
									|| c == '_'
									|| c == ':') {
								tagName.append(c);

							} else if (c == '>') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								state = XT;

							} else if (c == '/') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								state = XAS;

							} else if (c == ' ' || c == '\t' || c == '\n') {
								tags.push(tagName.toString());
								state = XA;

							} else {
								state = N;
							}
							break;
						case XT:
							if (c == '<') {
								state = XO;
							}
							break;
						case XO:
							if (Character.isLetter(c)
									|| c == '.'
									|| c == '-'
									|| c == '_'
									|| c == ':') {
								tagName.append(c);
								state = XO2;

							} else if (c == '/') {
								state = XC;

							} else if (c == '>') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								token(T_MALFORMED_XML, index + 1);
								state = N;

							} else if (c == ' ' || c == '\t' || c == '\n') {
								tags.push(tagName.toString());
								state = XA;

							}
							break;
						case XO2:
							if (Character.isLetterOrDigit(c)
									|| c == '.'
									|| c == '-'
									|| c == '_'
									|| c == ':') {
								tagName.append(c);

							} else if (c == '>') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								state = XT;

							} else if (c == '/') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								state = XAS;

							} else if (c == ' ' || c == '\t' || c == '\n') {
								tags.push(tagName.toString());
								tagName.setLength(0);
								state = XA;

							}
							break;
						case XC:
							if (Character.isLetter(c)
									|| c == '.'
									|| c == '-'
									|| c == '_'
									|| c == ':') {
								tagName.append(c);
								state = XC2;

							} else if (c == '>') {
								tagName.setLength(0);
								tags.pop();

								if (tags.isEmpty()) {
									token(T_XML, index + 1);
									state = N;
								} else {
									state = XT;
								}

							} else if (c == '/') {
								state = XCS;

							}
							break;
						case XC2:
							if (Character.isLetterOrDigit(c)
									|| c == '.'
									|| c == '-'
									|| c == '_'
									|| c == ':') {
								tagName.append(c);

							} else if (c == '>') {
								tagName.setLength(0);
								tags.pop();

								if (tags.isEmpty()) {
									token(T_XML, index + 1);
									state = N;
								} else {
									state = XT;
								}

							} else {
								state = XCA;

							}
							break;
						case XCS:
							if (c == '>') {
								tagName.setLength(0);
								tags.clear();

								token(T_XML, index + 1);
								state = N;

							} else {
								tagName.setLength(0);
								tags.clear();

								token(T_MALFORMED_XML, index + 1);
								state = N;
							}
							break;
						case XCA:
							if (c == '>') {
								tagName.setLength(0);
								tags.pop();

								if (tags.isEmpty()) {
									token(T_XML, index + 1);
									state = N;
								} else {
									state = XT;
								}

							} else if (c == '"') {
								state = XCAQ;

							} else if (c == '\'') {
								state = XCAA;

							}
							break;
						case XCAQ:
							if (c == '"') {
								state = XCA;
							}
							break;
						case XCAA:
							if (c == '\'') {
								state = XCAA;
							}
							break;
						case XA:
							if (c == '"') {
								state = XAQ;
							} else if (c == '\'') {
								state = XAA;
							} else if (c == '>') {
								state = XT;
							} else if (c == '/') {
								state = XAS;
							}
							break;
						case XAS:
							if (c == '>') {
								tagName.setLength(0);
								tags.pop();

								if (tags.isEmpty()) {
									token(T_XML, index + 1);
									state = N;
								} else {
									state = XT;
								}

							} else {
								state = XA;
							}
							break;
						case XAQ:
							if (c == '"') {
								state = XA;
							}
							break;
						case XAA:
							if (c == '\'') {
								state = XA;
							}
							break;
						}
						break;
					}
				}
				if (index <= string.length() + 1) {
					switch (state) {
					case N:
						token(T_UNKNOWN);
						break;
					case WS:
						break;
					case Q:
						token(T_QUOT_STRING);
						break;
					case A:
					case AS:
						token(T_APOS_STRING);
						break;
					case AA:
					case AAA:
						token(T_DOUBLE_APOS_STRING);
						break;
					case B:
					case BS:
						token(T_BACKTICK_STRING);
						break;
					case BB:
					case BBB:
						token(T_DOUBLE_BACKTICK_STRING);
						break;
					case C:
						token(T_COMMENT);
						break;
					case LP:
						token(T_OPENING_PARENTHESIS);
						break;
					case RP:
						token(T_CLOSING_PARENTHESIS);
						break;
					case LB:
						token(T_OPENING_BRACKET);
						break;
					case RB:
						token(T_CLOSING_BRACKET);
						break;
					case LC:
						token(T_OPENING_CURLY_BRACE);
						break;
					case RC:
						token(T_CLOSING_CURLY_BRACE);
						break;
					}
					index += 2;
				}
				return t;
			}
		};
	}
}
