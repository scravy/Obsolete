package net.scravy.hydrogen.compiler;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Compiler {

	public static class Warning {

		private final static int MAX_PREVIEW = 80;
		private final static int LINE_WIDTH = 50;

		final Message message;
		final Token token;
		final TokenType expected;
		final Exception exception;

		Warning(Message message, Token token) {
			this.message = message;
			this.token = token;
			this.expected = null;
			this.exception = null;
		}

		Warning(Message message, Token token,
				Exception exc) {
			this.message = message;
			this.token = token;
			this.expected = null;
			this.exception = exc;
		}

		Warning(Message message, Token token, TokenType expected) {
			this.message = message;
			this.token = token;
			this.expected = expected;
			this.exception = null;
		}

		public MessageType getMessageType() {
			return message.getMessageType();
		}

		public String getText() {
			return message.getMessage();
		}

		public Token getToken() {
			return token;
		}

		public TokenType getExpected() {
			return expected;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();

			b.append(message.getMessageType().name());
			b.append(":\n    ");
			b.append(wrap(message.getMessage(), LINE_WIDTH, "    "));
			if (exception != null) {
				b.append("\n\n    ");
				b.append(wrap(exception.getClass().getSimpleName() + ": "
						+ exception.getMessage(), LINE_WIDTH, "    "));
			}
			b.append("\n\n    ");
			b.append(token.type);
			b.append(" << ");
			String val = token.getValue().toString().replace("\n", "\\n");
			b.append(val.length() < MAX_PREVIEW
					? val : val.substring(0, MAX_PREVIEW));
			b.append(" >> \n    in file ");
			b.append(token.source.getAbsolutePath());
			b.append("\n    at line ");
			b.append(token.line);
			b.append(", column ");
			b.append(token.getPos());
			b.append(", position ");
			b.append(token.beginIndex);
			b.append(")");

			if (expected != null) {
				b.append("\n\n    expected ");
				b.append(expected);
			}

			return b.toString();
		}

		private static String wrap(String string, int length, String indent) {
			StringBuilder b = new StringBuilder();

			for (int i = 0; i < string.length(); i++) {

				if (i + length >= string.length()) {
					b.append(string.substring(i));
					break;
				}
				int p = string.indexOf('\n', i);

				if (p < 0 || p - i >= length) {
					p = string.substring(i, i + length).lastIndexOf(' ');
					if (p < 0) {
						p = string.indexOf(' ', i);
						int p2 = string.indexOf('\n', i);
						if (p2 >= 0 && p2 < p) {
							p = p2;
						}
					} else {
						p += i;
					}
					if (p < 0) {
						p = string.length();
					}
				}
				b.append(string.substring(i, p));
				b.append('\n');
				b.append(indent);
				i = p;
			}

			return b.toString();
		}
	}

	public static class Tokens implements Iterable<Token> {

		private final File source;
		private final List<Token> tokens;
		private final List<Warning> messages;
		private final Map<String, Object> properties;

		private int countErrors = -1;
		private int countWarnings = -1;
		private int countNotices = -1;

		Tokens(File source, List<Token> tokens, List<Warning> messages,
				Map<String, Object> properties) {
			this.source = source;
			this.tokens = tokens;
			this.messages = messages;
			this.properties = properties;
		}

		@Override
		public Iterator<Token> iterator() {
			return tokens.iterator();
		}

		public File getSource() {
			return source;
		}

		public List<Warning> getMessages() {
			return messages;
		}

		public Map<String, Object> getProperties() {
			return properties;
		}

		public int getCountErrors() {
			if (countErrors < 0) {
				countErrors = 0;
				for (Warning m : messages) {
					if (m.message.getMessageType() == MessageType.ERROR) {
						countErrors++;
					}
				}
			}
			return countErrors;
		}

		public int getCountWarnings() {
			if (countWarnings < 0) {
				countWarnings = 0;
				for (Warning m : messages) {
					if (m.message.getMessageType() == MessageType.WARNING) {
						countWarnings++;
					}
				}
			}
			return countWarnings;
		}

		public int getCountNotices() {
			if (countNotices < 0) {
				countNotices = 0;
				for (Warning m : messages) {
					if (m.message.getMessageType() == MessageType.NOTICE) {
						countNotices++;
					}
				}
			}
			return countNotices;
		}
	}

	public interface Target {

	}

	Set<TokenType> symbols = null;
	Set<TokenType> tokens = null;

	public Compiler() {

	}

	static class TokenTypeComparator implements Comparator<TokenType> {
		@Override
		public int compare(TokenType left, TokenType right) {
			return left.getName().compareTo(right.getName());
		}
	}

	public Set<TokenType> getSymbols() {
		if (this.symbols == null) {
			Set<TokenType> symbols = new TreeSet<TokenType>();
			for (TokenType t : BasicTokenType.values()) {
				symbols.add(t);
			}
			this.symbols = Collections.unmodifiableSet(symbols);
		}
		return symbols;
	}

	public Set<TokenType> getTokens() {
		if (this.symbols == null) {
			Set<TokenType> symbols = new TreeSet<TokenType>();
			for (TokenType t : ParserTokenType.values()) {
				symbols.add(t);
			}
			this.symbols = Collections.unmodifiableSet(symbols);
		}
		return symbols;
	}

	public Tokens tokenize(File file) throws IOException {
		Tokenizer1 tok1 = new Tokenizer1(file);
		Tokenizer2 tok2 = new Tokenizer2(tok1);

		return new Tokens(tok1.getSource(), tok2.tokenize(),
				tok2.getMessages(), tok2.getProperties());
	}

	public Tokens tokenize(String data) {
		Tokenizer1 tok1 = new Tokenizer1(data);
		Tokenizer2 tok2 = new Tokenizer2(tok1);

		return new Tokens(tok1.getSource(), tok2.tokenize(),
				tok2.getMessages(), tok2.getProperties());
	}

	public Document parse(File file) throws IOException {
		Parser1 parser1 = new Parser1();

		return parser1.parse(tokenize(file));
	}

	public Document parse(String data) {
		Parser1 parser1 = new Parser1();

		return parser1.parse(tokenize(data));
	}

	public Document parse(Tokens tokens) {
		Parser1 parser1 = new Parser1();

		return parser1.parse(tokens);
	}

	public <T extends Target> T compile(File file, T target) {
		return null;
	}
}
