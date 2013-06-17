package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.ParserTokenType.*;
import static net.scravy.hydrogen.compiler.TokenizerMessage.*;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.scravy.hydrogen.compiler.values.*;

enum BasicTokenType implements TokenTypeInternal {
	T_UNKNOWN {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			return TokenizerMessage.E_UNKNOWN_TOKEN_ENCOUNTERED;
		}
	},

	T_WHITE_SPACE,
	T_NEW_LINE,
	T_COMMENT,

	T_MAGIC_CONSTANT(true, "__[a-zA-Z0-9][a-zA-Z0-9_\\-\\.:]*__"),
	T_MAGIC_NAME(true, "__[a-zA-Z0-9]([a-zA-Z0-9]|_[a-zA-Z0-9])*"),

	T_ANY_NAME("[a-zA-Z]([a-zA-Z0-9]|[_\\-\\.:][a-zA-Z0-9])*"),

	T_MACRO_INCOVATION,

	T_BEGIN,
	T_END,
	T_DELIMITER,
	T_OPENING_PARENTHESIS,
	T_CLOSING_PARENTHESIS,
	T_OPENING_BRACKET,
	T_CLOSING_BRACKET,
	T_OPENING_CURLY_BRACE,
	T_CLOSING_CURLY_BRACE,

	T_MALFORMED_XML {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_XML);
			return E_MALFORMED_XML;
		}
	},
	T_XML {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_XML);
			return null;
		}
	},

	T_QUOT_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_STRING);
			return TokenUtil.parseQuotString(token);
		}
	},
	T_TRIPLE_QUOT_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_STRING);
			return TokenUtil.parseTripleQuotString(
					token, tokenizer.getTabSize());
		}
	},
	T_APOS_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_CHAR);
			return TokenUtil.parseAposString(token);
		}
	},
	T_DOUBLE_APOS_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_BYTE_STRING);
			return TokenUtil.parseDoubleAposString(token);
		}
	},
	T_BACKTICK_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			return TokenUtil.parseBacktickString(token);
		}
	},
	T_DOUBLE_BACKTICK_STRING {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_REGEX);
			return TokenUtil.parseDoubleBacktickString(token);
		}
	},

	T_COMMA {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_INFIX);
			token.setValue(new InfixOperator(","));
			return null;
		}
	},
	T_PREFIX_OPERATOR {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_PREFIX);
			token.setValue(new PrefixOperator(token.getDeclaredValue()));
			return null;
		}
	},
	T_INFIX_OPERATOR {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_INFIX);
			token.setValue(new InfixOperator(token.getDeclaredValue()));
			return null;
		}
	},
	T_POSTFIX_OPERATOR {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_SUFFIX);
			token.setValue(new PostfixOperator(token.getDeclaredValue()));
			return null;
		}
	},
	T_BINDING_INFIX_OPERATOR {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_INFIX);
			token.setValue(new BindingInfixOperator(token.getDeclaredValue()));
			return null;
		}
	},

	T_DATE_TIME(
			"([0-9]{3,})\\-([0-9]{2})\\-([0-9]{2})(T([0-9]{2})(:([0-9]{2})(:([0-9]{2})(\\.([0-9]+))?)?))?((\\+|\\-)([0-9]{2}):([0-9]{2}))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_DATE);
			return null;
		}
	},
	T_NEGATIVE_FLOAT("\\-([0-9]+)\\.([0-9]+)(f([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_NEGATIVE_FLOAT_EXP(
			"\\-([0-9]+)(\\.([0-9]+))?(e(\\+|\\-)?[0-9]+)(f([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_FLOAT("([0-9]+)\\.([0-9]+)(f([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_FLOAT_EXP("([0-9]+)(\\.([0-9]+))?(e(\\+|\\-)?[0-9]+)(f([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_VERSION_NUMBER("v([0-9](\\.[0-9])*(\\-[a-z][a-zA-Z]*)*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			token.setValue(new VersionNumber(token.getDeclaredValue()));
			return null;
		}
	},
	T_NEGATIVE_DECIMAL_NUMBER("(\\-([0-9]|_[0-9])+)(([suf])([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			Matcher m = getPattern().matcher(token.getDeclaredValue());
			m.matches();
			String stringValue = m.group(1).replace("_", "");
			if (m.group(4) != null) {
				if (m.group(4).equals("u")) {
					return E_NEGATIVE_NUMBER_MUST_BE_SIGNED;
				}
				int bitWidth = 0;
				try {
					bitWidth = Integer.parseInt(m.group(5));
				} catch (NumberFormatException exc) {
					return E_ILLEGAL_BIT_WIDTH;
				}
				switch (bitWidth) {
				case 32:
					try {
						Integer n = Integer.valueOf(stringValue);
						token.setValue(n);
						return null;
					} catch (NumberFormatException exc) {
						return E_NUMBER_CAN_NOT_BE_STORED_USING_GIVEN_BIT_WIDTH;
					}
				case 64:
					try {
						Long n = Long.valueOf(stringValue);
						token.setValue(n);
						return null;
					} catch (NumberFormatException exc) {
						return E_NUMBER_CAN_NOT_BE_STORED_USING_GIVEN_BIT_WIDTH;
					}
				default:
					return E_UNSUPPORTED_BIT_WIDTH;
				}
			} else {
				try {
					Integer n = Integer.valueOf(stringValue);
					token.setValue(n);
					return null;
				} catch (NumberFormatException exc) {
				}
				try {
					Long n = Long.valueOf(stringValue);
					token.setValue(n);
					return null;
				} catch (NumberFormatException exc) {
				}
				token.setValue(new BigInteger(stringValue));
			}
			return null;
		}
	},
	T_DECIMAL_NUMBER(true, "(([0-9]|_[0-9])+)(([suf])([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			Matcher m = getPattern().matcher(token.getDeclaredValue());
			m.matches();
			String stringValue = m.group(1).replace("_", "");
			if (m.group(4) != null) {
				int bitWidth = 0;
				try {
					bitWidth = Integer.parseInt(m.group(5));
				} catch (NumberFormatException exc) {
					return E_ILLEGAL_BIT_WIDTH;
				}
				switch (bitWidth) {
				case 32:
					try {
						Integer n = Integer.valueOf(stringValue);
						token.setValue(n);
						return null;
					} catch (NumberFormatException exc) {
						return E_NUMBER_CAN_NOT_BE_STORED_USING_GIVEN_BIT_WIDTH;
					}
				case 64:
					try {
						Long n = Long.valueOf(stringValue);
						token.setValue(n);
						return null;
					} catch (NumberFormatException exc) {
						return E_NUMBER_CAN_NOT_BE_STORED_USING_GIVEN_BIT_WIDTH;
					}
				default:
					return E_UNSUPPORTED_BIT_WIDTH;
				}
			} else {
				try {
					Integer n = Integer.valueOf(stringValue);
					token.setValue(n);
					return null;
				} catch (NumberFormatException exc) {
				}
				try {
					Long n = Long.valueOf(stringValue);
					token.setValue(n);
					return null;
				} catch (NumberFormatException exc) {
				}
				token.setValue(new BigInteger(stringValue));
			}
			return null;
		}
	},
	T_HEXADECIMAL_NUMBER("0x(([0-9a-fA-F]|_[0-9a-fA-F])+)(([su])([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_BINARY_NUMBER("0b(([01]|_[01])+)(([suf])([0-9]+))?") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},
	T_ARBITRARY_BASE_NUMBER("([0-9a-zA-Z]|_[0-9a-zA-Z])+#([0-9]+)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NUMBER);
			return null;
		}
	},

	T_PREFIXED_DASH_NAME(
			"([a-z][a-z0-9]*):([a-z][a-z0-9]*\\-[a-z0-9]([a-z0-9]|\\-[a-z0-9])*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			String[] parts = token.getDeclaredValue().split(":");
			token.setValue(new DashName(parts[0], parts[1]));
			return null;
		}
	},
	T_DASH_NAME("[a-z][a-z0-9]*\\-[a-z0-9]([a-z0-9]|\\-[a-z0-9])*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			token.setValue(new DashName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_UNDERSCORE_NAME(
			"([a-z][a-z0-9]*):([A-Z][A-Z0-9]*_[A-Z0-9]([A-Z0-9]|_[A-Z0-9])*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_CONSTANT);
			String[] parts = token.getDeclaredValue().split(":");
			token.setValue(new UnderscoreName(parts[0], parts[1]));
			return null;
		}
	},
	T_UNDERSCORE_NAME(true, "[A-Z][A-Z0-9]*_[A-Z0-9]([A-Z0-9]|_[A-Z0-9])*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_CONSTANT);
			token.setValue(new UnderscoreName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_ANNOTATION("@([a-z][a-z0-9]*):([A-Z][a-zA-Z0-9]*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_ANNOTATION);
			Matcher m = getPattern().matcher(token.getDeclaredValue());
			m.matches();
			token.setValue(new Annotation(m.group(1), m.group(2)));
			return null;
		}
	},
	T_ANNOTATION("@[A-Z][a-zA-Z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_ANNOTATION);
			token.setValue(new Annotation(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_TYPE_NAME("([a-z][a-z0-9]*):([A-Z][a-zA-Z0-9]*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_TYPE);
			String[] parts = token.getDeclaredValue().split(":");
			token.setValue(new TypeName(parts[0], parts[1]));
			return null;
		}
	},
	T_TYPE_NAME("[A-Z][a-zA-Z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_TYPE);
			token.setValue(new TypeName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_MEMBER_NAME(
			"@([a-z][a-z0-9]*):([a-z][a-z0-9]*[A-Z][a-zA-Z0-9]*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_MEMBER);
			Matcher m = getPattern().matcher(token.getDeclaredValue());
			m.matches();
			token.setValue(new MemberName(m.group(1), m.group(2)));
			return null;
		}
	},
	T_MEMBER_NAME("@[a-z][a-z0-9]*[A-Z][a-zA-Z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_MEMBER);
			token.setValue(new MemberName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_CAMEL_CASE_NAME("[a-z][a-z0-9]*:[a-z][a-z0-9]*[A-Z][a-zA-Z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			String[] parts = token.getDeclaredValue().split(":");
			token.setValue(new CamelCaseName(parts[0], parts[1]));
			return null;
		}
	},
	T_CAMEL_CASE_NAME("[a-z][a-z0-9]*[A-Z][a-zA-Z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			token.setValue(new CamelCaseName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_AT_NAME("@([a-z][a-z0-9]*):([a-z][a-z0-9]*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_MEMBER);
			Matcher m = getPattern().matcher(token.getDeclaredValue());
			m.matches();
			token.setValue(new AtName(m.group(1), m.group(2)));
			return null;
		}
	},
	T_AT_NAME("@[a-z][a-z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_MEMBER);
			token.setValue(new AtName(token.getDeclaredValue()));
			return null;
		}
	},
	T_PREFIXED_SIMPLE_NAME("([a-z][a-z0-9]*):([a-z][a-z0-9]*)") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			String[] parts = token.getDeclaredValue().split(":");
			token.setValue(new UnderscoreName(parts[0], parts[1]));
			return null;
		}
	},
	T_SIMPLE_NAME(true, "[a-z][a-z0-9]*") {
		@Override
		public Message process(Tokenizer2 tokenizer, Token token) {
			token.setType(P_NAME);
			token.setValue(new SimpleName(token.getDeclaredValue()));
			return null;
		}
	};

	final private boolean crucial;
	final private Pattern pattern;

	BasicTokenType() {
		this(false, null);
	}

	BasicTokenType(boolean crucial) {
		this(crucial, null);
	}

	BasicTokenType(String pattern) {
		this(false, pattern);
	}

	BasicTokenType(boolean crucial, String pattern) {
		this.crucial = crucial;
		this.pattern = pattern == null ? null : Pattern.compile(pattern);
	}

	@Override
	public boolean isCrucial() {
		return crucial;
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
		return pattern;
	}

	@Override
	public String getName() {
		return name();
	}
}