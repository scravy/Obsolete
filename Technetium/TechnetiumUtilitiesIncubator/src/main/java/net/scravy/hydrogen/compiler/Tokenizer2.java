package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.BasicTokenType.*;
import static net.scravy.hydrogen.compiler.TokenizerMessage.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.scravy.hydrogen.compiler.Compiler.Warning;
import net.scravy.hydrogen.compiler.TokenList.TokenLink;

@SuppressWarnings("serial")
class Tokenizer2 {

	private final Tokenizer1 tokenizer1;

	private String operators;
	private boolean indentationSyntaxEnabled;
	private boolean magicDeclarations;
	private boolean macrosEnabled;
	private int tabSize;
	private TokenTypeInternal bindingInfixOperatorType;
	private TokenTypeInternal prefixOperatorType;
	private TokenTypeInternal postfixOperatorType;

	private final LinkedList<Pattern> patterns = new LinkedList<Pattern>();
	private final LinkedList<TokenTypeInternal> patternTypes = new LinkedList<TokenTypeInternal>();

	private final ArrayList<Warning> messages = new ArrayList<Warning>();
	private final Map<String, Object> properties = new TreeMap<String, Object>();

	TokenList tokens = null;

	private boolean foundMagic = false;

	Tokenizer2(Tokenizer1 tok) {
		this.tokenizer1 = tok;

		init();
	}

	public void reset() {
		patterns.clear();
		patternTypes.clear();
		messages.clear();

		init();
	}

	private void init() {
		this.operators = "+-*/|&%$!?:.<>=~^#";
		this.indentationSyntaxEnabled = true;
		this.magicDeclarations = true;
		this.macrosEnabled = true;
		this.tabSize = 2;

		this.bindingInfixOperatorType = T_BINDING_INFIX_OPERATOR;
		this.prefixOperatorType = T_PREFIX_OPERATOR;
		this.postfixOperatorType = T_POSTFIX_OPERATOR;

		getProperties().clear();

		addPattern(T_DATE_TIME);
		addPattern(T_FLOAT_EXP);
		addPattern(T_FLOAT);
		addPattern(T_VERSION_NUMBER);
		addPattern(T_DECIMAL_NUMBER);
		addPattern(T_HEXADECIMAL_NUMBER);
		addPattern(T_BINARY_NUMBER);
		addPattern(T_ARBITRARY_BASE_NUMBER);
		addPattern(T_MAGIC_CONSTANT);
		addPattern(T_MAGIC_NAME);
		addPattern(T_DASH_NAME);
		addPattern(T_PREFIXED_UNDERSCORE_NAME);
		addPattern(T_UNDERSCORE_NAME);
		addPattern(T_PREFIXED_ANNOTATION);
		addPattern(T_ANNOTATION);
		addPattern(T_PREFIXED_TYPE_NAME);
		addPattern(T_TYPE_NAME);
		addPattern(T_PREFIXED_MEMBER_NAME);
		addPattern(T_MEMBER_NAME);
		addPattern(T_PREFIXED_CAMEL_CASE_NAME);
		addPattern(T_CAMEL_CASE_NAME);
		addPattern(T_PREFIXED_AT_NAME);
		addPattern(T_AT_NAME);
		addPattern(T_PREFIXED_SIMPLE_NAME);
		addPattern(T_SIMPLE_NAME);
	}

	private void addPattern(TokenTypeInternal type) {
		patterns.add(type.getPattern());
		patternTypes.add(type);
	}

	private void addPatternFirst(String pattern, TokenTypeInternal type) {
		Pattern p = Pattern.compile(pattern);
		patterns.addFirst(p);
		patternTypes.addFirst(type);
	}

	private void addPatternFirst(Pattern p, TokenTypeInternal type) {
		patterns.addFirst(p);
		patternTypes.addFirst(type);
	}

	private boolean isOperator(char c) {
		return operators.indexOf(c) >= 0;
	}

	private static boolean isWhiteSpaceAfter(TokenType type) {
		if (type instanceof BasicTokenType) {
			switch ((BasicTokenType) type) {
			case T_WHITE_SPACE:
			case T_COMMENT:
			case T_NEW_LINE:
			case T_CLOSING_BRACKET:
			case T_CLOSING_CURLY_BRACE:
			case T_CLOSING_PARENTHESIS:
			case T_OPENING_CURLY_BRACE:
				return true;
			}
		}
		return false;
	}

	private static boolean isWhiteSpaceBefore(TokenType type) {
		if (type instanceof BasicTokenType) {
			switch ((BasicTokenType) type) {
			case T_WHITE_SPACE:
			case T_COMMENT:
			case T_NEW_LINE:
			case T_OPENING_BRACKET:
			case T_OPENING_CURLY_BRACE:
			case T_OPENING_PARENTHESIS:
			case T_CLOSING_CURLY_BRACE:
				return true;
			}
		}
		return false;
	}

	public void setTabSize(int num) {
		this.tabSize = num;
	}

	public int getTabSize() {
		return tabSize;
	}

	public boolean getMacrosEnabled() {
		return macrosEnabled;
	}

	public void setMacrosEnabled(boolean macrosEnabled) {
		this.macrosEnabled = macrosEnabled;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public boolean getIndentationSyntaxEnabled() {
		return indentationSyntaxEnabled;
	}

	public void setIndentationSyntaxEnabled(boolean indentationSyntax) {
		this.indentationSyntaxEnabled = indentationSyntax;
	}

	public List<Warning> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public List<Token> tokenize() {
		if (tokens != null) {
			throw new IllegalStateException();
		}
		tokens = new TokenList();

		for (Token token : tokenizer1) {
			tokens.add(token);
		}

		if (tokens.isEmpty()) {
			return new ArrayList<Token>(0);
		}

		operatorsAndBasicTokens();
		doLayoutAndSpecialSyntax();
		processTokens();

		ArrayList<Token> tokenList = new ArrayList<Token>(tokens.size);
		if (!tokens.isEmpty()) {
			TokenLink link = tokens.first;
			do {
				tokenList.add(link.tok);
				link = link.next;
			} while (link != null);
		}
		return tokenList;
	}

	private void message(Message message, Token token) {
		messages.add(new Warning(message, token));
	}

	private void message(Message message, Token token, Exception exc) {
		messages.add(new Warning(message, token, exc));
	}

	private void message(Message message, Token token,
			TokenTypeInternal expected) {
		messages.add(new Warning(message, token, expected));
	}

	private void parseRulesFile(File baseDir, Token rulesFileToken) {

		String rulesFile = rulesFileToken.getValue().toString();
		File file = null;
		do {
			file = new File(baseDir, rulesFile);
			baseDir = baseDir.getParentFile();
		} while (!file.exists() && baseDir != null);

		rulesFileToken.setValue(file.getAbsolutePath());
		rulesFileToken.setType(MagicTokenType.M_RULES_FILE);

		if (file.canRead()) {
			try {
				Tokenizer1 rulesTok = new Tokenizer1(file);

				List<List<Token>> lines = new ArrayList<List<Token>>();
				List<Token> currentLine = new ArrayList<Token>();
				lines.add(currentLine);
				for (Token tok : rulesTok) {
					if (tok.type == T_NEW_LINE || tok.type == T_COMMENT) {
						currentLine = new ArrayList<Token>();
						lines.add(currentLine);
						continue;
					}
					if (tok.type == T_WHITE_SPACE) {
						continue;
					}
					currentLine.add(tok);
				}
				lines: for (List<Token> line : lines) {
					if (line.isEmpty()) {
						continue;
					}
					Token magicToken = line.get(0);
					String firstTokenText = magicToken.getDeclaredValue();
					if (T_ANY_NAME.getPattern().matcher(firstTokenText)
							.matches()) {
						if (line.size() == 2) {
							Token valueToken = line.get(1);

							for (BasicTokenType type : BasicTokenType.values()) {
								String value = valueToken.getDeclaredValue();
								if (type.getPattern() != null) {
									if (type.getPattern().matcher(value)
											.matches()) {
										valueToken.setType(type);
										Message m = type.process(this,
												valueToken);
										if (m != null) {
											message(m, valueToken);
											break;
										}
									}
								}
							}
							if (valueToken.type == T_UNKNOWN) {
								message(W_UNKNOWN_TOKEN_IN_RULES_PROPERTY,
										valueToken);
							} else {
								properties.put(
										magicToken.getDeclaredValue(),
										valueToken.getValue());
							}
						} else {
							message(W_RULES_PROPERTY_DECLARATION_LINE_CONTAINS_MORE_THAN_TWO_TOKENS,
									magicToken);
						}
						continue lines;
					} else if (!T_MAGIC_NAME.getPattern()
							.matcher(firstTokenText)
							.matches()) {
						message(W_RULES_FILE_CONTAINS_NON_MAGIC_DECLARATION,
								magicToken);
						continue;
					}
					magicToken.setType(T_MAGIC_NAME);
					line.remove(0);
					for (Token tok : line) {
						for (BasicTokenType type : BasicTokenType.values()) {
							if (type.isCrucial()) {
								String value = tok.getDeclaredValue();
								if (type.getPattern().matcher(value).matches()) {
									tok.setType(type);
								}
							}
						}
					}
					handleMagic(magicToken, line);
				}
			} catch (IOException exc) {
				message(W_IOEXCEPTION_WHILE_READING_RULES_FILE, rulesFileToken);
			}
		} else {
			message(W_CAN_NOT_READ_RULES_FILE, rulesFileToken);
		}
	}

	private void handleMagic(Token magicToken, List<Token> magicTokens) {

		String name = magicToken.getDeclaredValue();

		if (magicToken.getPos() != 1) {
			return;
		}

		if (!magicDeclarations) {
			message(W_TOKENIZER_MAGIC_DECLARATION_ENCOUNTERED_ALTHOUGH_MAGIC_IS_TURNED_OFF,
					magicToken);
		}

		if (name.equals("__rules")) {
			if (magicTokens.size() == 1
					&& magicTokens.get(0).type == T_QUOT_STRING) {
				Message m = magicTokens.get(0).process(this);
				if (m != null) {
					message(m, magicTokens.get(0));
				} else if (magicDeclarations) {
					Token rulesFileToken = magicTokens.get(0);
					try {
						parseRulesFile(tokenizer1.getSourceDirectory(),
								rulesFileToken);
						message(I_RULES_FILE_PROCESSED, rulesFileToken);
					} catch (Exception exc) {
						exc.printStackTrace(System.out);
						message(W_EXCEPTION_WHILE_PROCESSING_RULES_FILE,
								rulesFileToken);
					}
				}
			} else {
				message(E_ILLEGAL_RULES_DECLARATION, magicToken);
			}

		} else if (name.equals("__tabsize")) {
			if (magicTokens.size() == 1
					&& magicTokens.get(0).type == T_DECIMAL_NUMBER) {
				Message m = magicTokens.get(0).process(this);
				if (m != null) {
					message(m, magicTokens.get(0));
				} else if (magicDeclarations) {
					Object v = magicTokens.get(0).getValue();
					if (v instanceof Integer) {
						tabSize = (Integer) v;
						message(I_TABSIZE_CHANGED, magicTokens.get(0));
					}
				}

			} else if (magicTokens.isEmpty()) {
				message(E_ILLEGAL_TABSIZE_DECLARATION_MISSING_SIZE,
						magicToken);
			} else {
				message(E_ILLEGAL_TABSIZE_DECLARATION,
						magicTokens.get(0), T_DECIMAL_NUMBER);
			}

		} else if (name.equals("__no_indentation_syntax")) {
			if (magicTokens.isEmpty()) {
				if (magicDeclarations) {
					indentationSyntaxEnabled = false;
					message(I_INDENTATION_SYNTAX_TURNED_OFF, magicToken);
				}
			} else {
				message(E_ILLEGAL_NO_INDENTATION_SYNTAX_DECLARATION,
						magicTokens.get(0));
			}

		} else if (name.equals("__no_tokenizer_magic")) {
			if (magicTokens.isEmpty()) {
				if (magicDeclarations) {
					magicDeclarations = false;
					message(I_TOKENIZER_MAGIC_TURNED_OFF, magicToken);
				}
			} else {
				message(E_ILLEGAL_NO_TOKENIZER_MAGIC_DECLARATIONS_DECLARATION,
						magicToken);
			}

		} else if (name.equals("__no_macros")) {
			if (magicTokens.isEmpty()) {
				if (magicDeclarations) {
					macrosEnabled = false;
					message(I_MACROS_TURNED_OFF, magicToken);
				}
			} else {
				message(E_ILLEGAL_NO_MACROS_DECLARATION,
						magicTokens.get(0));
			}

		} else if (name.equals("__no_binding_infix_operators")) {
			if (magicTokens.isEmpty()) {
				if (magicDeclarations) {
					bindingInfixOperatorType = T_INFIX_OPERATOR;
					message(I_BINDING_INFIX_OPERATORS_TURNED_OFF, magicToken);
				}
			} else {
				message(E_ILLEGAL_NO_BINDING_INFIX_OPERATORS_DECLARATION,
						magicToken);
			}

		} else if (name.equals("__token")) {
			if (magicTokens.size() == 2) {
				Token regex = magicTokens.get(0);
				Token typeName = magicTokens.get(1);
				Message m = regex.process(this);
				if (m != null) {
					message(m, regex);
				} else if (regex.type == T_QUOT_STRING
						&& typeName.type == T_UNDERSCORE_NAME) {
					if (magicDeclarations) {
						String regexString = regex.getValue().toString();
						addPatternFirst(
								Pattern.quote(regexString),
								new CustomTokenType(typeName
										.getDeclaredValue()));
					}
				} else if (regex.type == T_DOUBLE_BACKTICK_STRING
						&& typeName.type == T_UNDERSCORE_NAME) {
					if (magicDeclarations) {
						Pattern pattern = (Pattern) regex.getValue();
						addPatternFirst(
								pattern,
								new CustomTokenType(typeName.getDeclaredValue()));
					}
				} else {
					message(E_ILLEGAL_TOKEN_DECLARATION, magicToken);
				}
			}

		} else if (name.equals("__clear_tokens")) {
			ArrayList<Integer> remove = new ArrayList<Integer>();
			if (magicTokens.size() == 0) {
				if (magicDeclarations) {
					for (int i = 0; i < patterns.size(); i++) {
						if (patternTypes.get(i).isUserDefined()) {
							remove.add(i);
						}
					}
				}
			} else if (magicTokens.size() == 1
					&& magicTokens.get(0).type == T_SIMPLE_NAME
					&& magicTokens.get(0).getValue().equals("all")) {
				if (magicDeclarations) {
					for (int i = 0; i < patterns.size(); i++) {
						if (!patternTypes.get(i).isCrucial()) {
							remove.add(i);
						}
					}
				}
			} else {
				message(E_ILLEGAL_CLEAR_TOKENS_DECLARATION, magicToken);
			}
			if (!remove.isEmpty()) {
				for (int i = remove.size() - 1; i >= 0; i--) {
					patternTypes.remove(remove.get(i).intValue());
					patterns.remove(remove.get(i).intValue());
				}
			}

		} else if (name.equals("__operators")) {
			if (magicTokens.size() == 1) {
				Token string = magicTokens.get(0);
				Message m = string.process(this);

				if (m != null) {
					message(m, string);
				} else if (string.type == T_QUOT_STRING) {
					if (magicDeclarations) {
						operators = string.getValue().toString();
					}
				} else {
					message(E_ILLEGAL_CLEAR_TOKENS_DECLARATION, magicToken);
				}
			}

		} else if (name.equals("__add_operators")) {
			Token string = magicTokens.get(0);
			Message m = string.process(this);

			if (m != null) {
				message(m, string);
			} else if (string.type == T_QUOT_STRING) {
				if (magicDeclarations) {
					operators = string.getValue().toString();
				}
			} else {
				message(E_ILLEGAL_ADD_OPERATORS_DECLARATION, magicToken);
			}

		} else if (name.equals("__remove_operators")) {
			Token string = magicTokens.get(0);
			Message m = string.process(this);

			if (m != null) {
				message(m, string);
			} else if (string.type == T_QUOT_STRING) {
				if (magicDeclarations) {
					StringBuilder b = new StringBuilder();
					String removeOps = string.getValue().toString();
					for (int i = 0; i < operators.length(); i++) {
						if (removeOps.indexOf(operators.charAt(i)) < 0) {
							b.append(operators.charAt(i));
						}
					}
					operators = b.toString();
				}
			} else {
				message(E_ILLEGAL_REMOVE_OPERATORS_DECLARATION, magicToken);
			}
		} else {
			message(N_UNKNOWN_MAGIC_DECLARATION, magicToken);
		}
	}

	private void processMagicConstant(Token token) {
		String name = token.getDeclaredValue(2, -2);

		if (name.equals("LINE")) {
			token.setType(MagicTokenType.M_NUMBER);
			token.setValue(token.line);

		} else if (name.equals("FILE")) {
			token.setType(MagicTokenType.M_FILE);
			token.setValue(tokenizer1.getSource().getPath());

		} else if (name.equals("DIR")) {
			token.setType(MagicTokenType.M_DIRECTORY);
			token.setValue(tokenizer1.getSourceDirectory().getPath());

		} else if (name.equals("FILENAME")) {
			token.setType(MagicTokenType.M_STRING);
			token.setValue(tokenizer1.getSource().getName());

		} else if (name.equals("DATETIME")) {
			token.setType(MagicTokenType.M_DATETIME);
			token.setValue(new Date());

		} else if (name.equals("OPERATORS")) {
			token.setType(MagicTokenType.M_STRING);
			token.setValue(operators);

		} else {
			message(N_UNKNOWN_MAGIC_CONSTANT, token);
		}
	}

	private void processMagicDirective(TokenLink link) {
		foundMagic = false;

		LinkedList<Token> magicTokens = new LinkedList<Token>();
		Token magicToken = null;

		do {
			if (link.tok.type == T_MAGIC_NAME) {
				magicToken = link.tok;
				break;
			}
			if (link.tok.type != T_WHITE_SPACE
					&& link.tok.type != T_NEW_LINE
					&& link.tok.type != T_COMMENT) {
				magicTokens.addFirst(link.tok);
			}
			link = link.prev;
		} while (link != null);

		handleMagic(magicToken, magicTokens);
	}

	private void operatorsAndBasicTokens() {
		TokenLink link = tokens.first;

		do {
			Token t = link.tok;

			if (t.type instanceof BasicTokenType) {
				switch ((BasicTokenType) t.type) {
				case T_NEW_LINE:
				case T_COMMENT: {
					t.setType(T_NEW_LINE);

					clearWhiteSpaceBefore(link);

					if (foundMagic) {
						processMagicDirective(link);
					}
					break;
				}

				case T_UNKNOWN: {
					int startsWith = 0;
					for (int i = t.beginIndex; i < t.endIndex
							&& isOperator(t.string.charAt(i)); i++) {
						startsWith++;
					}
					if (startsWith == t.getLength()) {
						boolean whiteSpaceBefore = isWhiteSpaceBefore(
								link.typeOfPrevious());
						boolean whitespaceAfter = isWhiteSpaceAfter(
								link.typeOfNext());
						if (whitespaceAfter && whiteSpaceBefore) {
							t.setType(T_INFIX_OPERATOR);
						} else if (whitespaceAfter) {
							t.setType(postfixOperatorType);
						} else if (whiteSpaceBefore) {
							t.setType(prefixOperatorType);
						} else {
							t.setType(bindingInfixOperatorType);
						}
						break;
					}
					int endsWith = 0;
					for (int i = t.endIndex - 1; i >= t.beginIndex
							&& isOperator(t.string.charAt(i)); i--) {
						endsWith++;
					}
					if (startsWith > 0) {
						TokenTypeInternal type = prefixOperatorType;
						if (!isWhiteSpaceBefore(link.typeOfPrevious())) {
							type = bindingInfixOperatorType;
						}
						Token before = new Token(t.string, type, t.source,
								t.line,
								t.beginIndex, t.beginIndex + startsWith);
						Token newCurrent = new Token(t.string, t.type,
								t.source,
								t.line, t.beginIndex + startsWith, t.endIndex);
						link = link.replace(before, newCurrent);
						t = link.tok;
					}
					if (endsWith > 0) {
						TokenTypeInternal type = postfixOperatorType;
						if (!isWhiteSpaceAfter(link.typeOfNext())) {
							type = bindingInfixOperatorType;
						}
						Token after = new Token(t.string, type, t.source,
								t.line,
								t.endIndex - endsWith, t.endIndex);
						Token newCurrent = new Token(t.string, t.type,
								t.source,
								t.line, t.beginIndex, t.endIndex - endsWith);
						link = link.replace(newCurrent, after).prev;
						t = link.tok;
					}
					processYetUnknownToken(link);
					break;
				}
				case T_CLOSING_CURLY_BRACE:
				case T_OPENING_CURLY_BRACE: {
					clearWhiteSpaceBefore(link);
					clearWhiteSpaceAfter(link);
					break;
				}
				case T_CLOSING_BRACKET:
				case T_CLOSING_PARENTHESIS: {
					clearWhiteSpaceBefore(link);
					break;
				}
				case T_OPENING_BRACKET:
				case T_OPENING_PARENTHESIS: {
					clearWhiteSpaceAfter(link);
					break;
				}
				}
			}

			link = link.next;
		} while (link != null);

		if (foundMagic) {
			processMagicDirective(tokens.last);
		}
	}

	private void clearWhiteSpaceBefore(TokenLink backLink) {
		while (backLink.prev != null) {
			backLink = backLink.prev;

			if (backLink.tok.type == T_NEW_LINE
					|| backLink.tok.type == T_COMMENT
					|| backLink.tok.type == T_WHITE_SPACE) {
				backLink.remove();
			} else {
				break;
			}
		}
	}

	private void clearWhiteSpaceAfter(TokenLink backLink) {
		while (backLink.next != null) {
			backLink = backLink.next;

			if (backLink.tok.type == T_NEW_LINE
					|| backLink.tok.type == T_COMMENT
					|| backLink.tok.type == T_WHITE_SPACE) {
				backLink.remove();
			} else {
				break;
			}
		}
	}

	/*
	 * This method is the slowest method of them all but
	 * it also provides the greatest flexibility. About
	 * half the time tokenizing a stream is spent in this
	 * function.
	 */
	private void processYetUnknownToken(TokenLink link) {
		String value = link.tok.getDeclaredValue();
		ArrayList<Token> newTokens = new ArrayList<Token>();

		Matcher m;

		int beginIndex = link.tok.beginIndex;
		outerLoop: for (;;) {
			for (int pi = 0; pi < patterns.size(); pi++) {
				Pattern p = patterns.get(pi);
				m = p.matcher(value);
				if (m.lookingAt()) {
					String match = m.group(0);
					if (match.length() == value.length()) {
						newTokens.add(new Token(link.tok.string,
								patternTypes.get(pi), link.tok.source,
								link.tok.line, beginIndex,
								beginIndex + match.length()));
						break outerLoop;
					} else {
						int opLength = 0;
						for (int i = match.length(); i < value.length()
								&& isOperator(value.charAt(i)); i++) {
							opLength++;
						}
						if (opLength == 0) {
							continue;
						}
						newTokens.add(new Token(link.tok.string,
								patternTypes.get(pi), link.tok.source,
								link.tok.line,
								beginIndex, beginIndex + match.length()));
						beginIndex += match.length();
						newTokens.add(new Token(link.tok.string,
								bindingInfixOperatorType, link.tok.source,
								link.tok.line,
								beginIndex, beginIndex + opLength));
						beginIndex += opLength;
						value = value.substring(match.length() + opLength);
						continue outerLoop;
					}
				}
			}
			break;
		}
		if (!newTokens.isEmpty()) {
			if (newTokens.get(newTokens.size() - 1).endIndex != link.tok.endIndex) {
				newTokens.add(new Token(link.tok.string, T_UNKNOWN,
						link.tok.source, link.tok.line,
						beginIndex, link.tok.endIndex));
			}
			for (Token newToken : newTokens) {
				if (newToken.type == T_MAGIC_NAME) {
					foundMagic = true;
				} else if (newToken.type == T_MAGIC_CONSTANT) {
					processMagicConstant(newToken);
				}
			}
			link.replace(newTokens.toArray(new Token[newTokens.size()]));
		}
	}

	private TokenTypeInternal counterPart(TokenType braceType) {
		if (braceType instanceof BasicTokenType) {
			switch ((BasicTokenType) braceType) {
			case T_OPENING_CURLY_BRACE:
				return T_CLOSING_CURLY_BRACE;
			case T_OPENING_BRACKET:
				return T_CLOSING_BRACKET;
			case T_OPENING_PARENTHESIS:
				return T_CLOSING_PARENTHESIS;
			case T_CLOSING_CURLY_BRACE:
				return T_OPENING_CURLY_BRACE;
			case T_CLOSING_BRACKET:
				return T_OPENING_BRACKET;
			case T_CLOSING_PARENTHESIS:
				return T_OPENING_PARENTHESIS;
			}
		}
		return null;
	}

	private void doLayoutAndSpecialSyntax() {
		TokenLink link = tokens.first;

		ArrayDeque<Integer> indentation = new ArrayDeque<Integer>();
		indentation.add(0);

		ArrayDeque<Token> braces = new ArrayDeque<Token>();

		do {
			Token t = link.tok;

			if (t.type instanceof BasicTokenType) {
				switch ((BasicTokenType) t.type) {
				case T_SIMPLE_NAME:
				case T_PREFIXED_SIMPLE_NAME:
					if (macrosEnabled && link.next != null
							&& link.next.tok.type == T_OPENING_PARENTHESIS) {
						link.replace(new Token(t.string, T_MACRO_INCOVATION,
								t.source, t.line, t.beginIndex, t.endIndex));
					}
					break;
				case T_DECIMAL_NUMBER:
					if (link.prev != null
							&& link.prev.tok.type == T_PREFIX_OPERATOR
							&& link.prev.tok.getDeclaredValue().equals("-")) {
						link.prev.remove();
						t = link.replace(new Token(t.string,
								T_NEGATIVE_DECIMAL_NUMBER,
								t.source, t.line, t.beginIndex - 1, t.endIndex)).tok;
					}
					break;
				case T_FLOAT:
					if (link.prev != null
							&& link.prev.tok.type == T_PREFIX_OPERATOR
							&& link.prev.tok.getDeclaredValue().equals("-")) {
						link.prev.remove();
						t = link.replace(new Token(t.string,
								T_NEGATIVE_FLOAT,
								t.source, t.line, t.beginIndex - 1, t.endIndex)).tok;
					}
					break;
				case T_FLOAT_EXP:
					if (link.prev != null
							&& link.prev.tok.type == T_PREFIX_OPERATOR
							&& link.prev.tok.getDeclaredValue().equals("-")) {
						link.prev.remove();
						t = link.replace(new Token(t.string,
								T_NEGATIVE_FLOAT_EXP,
								t.source, t.line, t.beginIndex - 1, t.endIndex)).tok;
					}
					break;
				case T_OPENING_CURLY_BRACE:
					link.replace(new Token(t.string, T_BEGIN,
							t.source, t.line, t.beginIndex, t.endIndex));
				case T_OPENING_BRACKET:
				case T_OPENING_PARENTHESIS:
					braces.push(t);
					break;
				case T_CLOSING_CURLY_BRACE:
					link.replace(new Token(t.string, T_END,
							t.source, t.line, t.beginIndex, t.endIndex));
				case T_CLOSING_BRACKET:
				case T_CLOSING_PARENTHESIS: {
					Token tt = braces.poll();
					if (tt == null) {
						message(E_UNBALANCED_CLOSING_BRACE, t);
					} else if (tt.type == T_OPENING_BRACKET
							&& t.type != T_CLOSING_BRACKET
							|| tt.type == T_OPENING_CURLY_BRACE
							&& t.type != T_CLOSING_CURLY_BRACE
							|| tt.type == T_OPENING_PARENTHESIS
							&& t.type != T_CLOSING_PARENTHESIS) {
						message(E_MISMATCHED_BRACE, t, counterPart(tt.type));
					}
					break;
				}
				case T_WHITE_SPACE: {
					link.remove();
					break;
				}
				case T_NEW_LINE: {
					if (braces.isEmpty() && indentationSyntaxEnabled) {
						TokenLink next = link.next;

						int indent = 0;
						if (next != null && next.tok.type == T_WHITE_SPACE) {
							for (int i = next.tok.beginIndex; i < next.tok.endIndex; i++) {
								indent += next.tok.string.charAt(i) == '\t'
										? tabSize : 1;
							}
							if (next.next != null
									&& next.next.tok.type == T_INFIX_OPERATOR
									&& indent > indentation
											.peek()) {
								link.remove();
								break;
							}
						}
						if (indent == indentation.peek()) {
							link.replace(new Token(link.tok.string,
									T_DELIMITER, link.tok.source,
									link.tok.line, link.tok.beginIndex,
									link.tok.endIndex));
						} else if (indent > indentation.peek()) {
							indentation.push(indent);
							link.replace(new Token(link.tok.string, T_BEGIN,
									link.tok.source, link.tok.line,
									link.tok.beginIndex, link.tok.endIndex));
						} else {
							while (indent < indentation.peek()) {
								indentation.pop();
								link.insertBefore(new Token(link.tok.string,
										T_END, link.tok.source, link.tok.line,
										link.tok.beginIndex, link.tok.endIndex));
							}
							link.remove();
						}
					} else {
						link.replace(new Token(link.tok.string, T_DELIMITER,
								link.tok.source, link.tok.line,
								link.tok.beginIndex,
								link.tok.endIndex));
					}
					break;
				}
				}
			}
			link = link.next;
		} while (link != null);

		for (Token brace : braces) {
			message(E_UNBALANCED_OPENING_BRACE, brace);
		}

		if (indentationSyntaxEnabled && !tokens.isEmpty()) {
			Token lastToken = tokens.last.tok;
			while (indentation.peek() > 0) {
				indentation.pop();
				tokens.last.insertAfter(new Token(
						lastToken.string, T_END, lastToken.source,
						lastToken.line, lastToken.beginIndex,
						lastToken.endIndex));
			}
		}
	}

	private void processTokens() {
		TokenLink link = tokens.first;
		do {
			Token token = link.tok;

			if (token.value == null) {
				try {
					Message m = token.process(this);
					if (m != null) {
						message(m, token);
					}
				} catch (Exception exc) {
					message(E_EXCEPTION_WHILE_PROCESSING_TOKEN, token, exc);
				}
			}

			link = link.next;
		} while (link != null);
	}
}
