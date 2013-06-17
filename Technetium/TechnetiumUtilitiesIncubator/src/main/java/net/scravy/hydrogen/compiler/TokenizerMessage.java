package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.MessageType.*;

public enum TokenizerMessage implements Message {

	I_TABSIZE_CHANGED(INFO,
			"Tabsize set. A tab character (\\x09) will now be regarded as the"
					+ " specified number of spaces (\\x20) when parsing"
					+ " indentation and triple quot strings."),
	I_RULES_FILE_PROCESSED(INFO,
			"Processed a rules file according to __rules declaration."
					+ " Let there be law and order."),
	I_INDENTATION_SYNTAX_TURNED_OFF(
			INFO,
			"I see you like it the old school way, man."
					+ " Indentation based syntax will not be recognized anymore"
					+ " through out the whole file. The only way to create"
					+ " blocks is thus the use of {curly} braces or by"
					+ " building a cobblestone generator."),
	I_TOKENIZER_MAGIC_TURNED_OFF(INFO,
			"Any magic declaration following this declaration and"
					+ " targetting the tokenizer will be ignored."
					+ " In fact, you can't even turn it on again since"
					+ " all the magic is gone. I hope you're happy now."),
	I_BINDING_INFIX_OPERATORS_TURNED_OFF(
			INFO,
			"Binding infix operators are were turned off, that is any"
					+ " such operator will be regarded as an ordinary infix operator"
					+ " by the tokenizer."),
	I_MACROS_TURNED_OFF(INFO,
			"Macros were turned off. Note that if your script relies on macros"
					+ " this will most certainly break the script."
					+ " If you're not using macros at all you're fine."),

	W_TOKENIZER_MAGIC_DECLARATION_ENCOUNTERED_ALTHOUGH_MAGIC_IS_TURNED_OFF(
			WARNING,
			"A magic declaration was encountered although"
					+ " magic declarations have been turned off."
					+ " This may alter the way your file is compiled."),

	W_CAN_NOT_READ_RULES_FILE(WARNING),
	W_IOEXCEPTION_WHILE_READING_RULES_FILE(WARNING),
	W_RULES_FILE_CONTAINS_NON_MAGIC_DECLARATION(WARNING),
	W_EXCEPTION_WHILE_PROCESSING_RULES_FILE(WARNING),
	W_UNKNOWN_TOKEN_IN_RULES_PROPERTY(WARNING),
	W_RULES_PROPERTY_DECLARATION_LINE_CONTAINS_MORE_THAN_TWO_TOKENS(WARNING),

	E_ILLEGAL_RULES_DECLARATION(ERROR),
	E_ILLEGAL_TABSIZE_DECLARATION(ERROR),
	E_ILLEGAL_TABSIZE_DECLARATION_MISSING_SIZE(ERROR),
	E_ILLEGAL_NO_INDENTATION_SYNTAX_DECLARATION(ERROR),
	E_ILLEGAL_NO_TOKENIZER_MAGIC_DECLARATIONS_DECLARATION(ERROR),
	E_ILLEGAL_NO_MACROS_DECLARATION(ERROR),
	E_ILLEGAL_NO_BINDING_INFIX_OPERATORS_DECLARATION(ERROR),
	E_ILLEGAL_TOKEN_DECLARATION(ERROR),
	E_ILLEGAL_CLEAR_TOKENS_DECLARATION(ERROR),
	E_ILLEGAL_ADD_OPERATORS_DECLARATION(ERROR),
	E_ILLEGAL_REMOVE_OPERATORS_DECLARATION(ERROR),
	E_ILLEGAL_OPERATORS_DECLARATION(ERROR),

	N_UNKNOWN_MAGIC_DECLARATION(NOTICE),
	N_UNKNOWN_MAGIC_CONSTANT(NOTICE),

	E_MALFORMED_XML(ERROR),

	E_MISMATCHED_BRACE(ERROR),
	E_UNBALANCED_CLOSING_BRACE(ERROR),
	E_UNBALANCED_OPENING_BRACE(ERROR),

	E_UNKNOWN_TOKEN_ENCOUNTERED(
			ERROR,
			"The tokenizer found a Token which it does not recognize."
					+ " No rule is known that specifies how to handle the token."),

	E_NEGATIVE_NUMBER_MUST_BE_SIGNED(ERROR,
			"A negative number can not be unsigned. However, an"
					+ " unsigned modifier has been specified. Should the"
					+ " number really be a negative one? If so, remove the"
					+ " 'u' or replace it by 's' (for signed)."),
	E_ILLEGAL_BIT_WIDTH(ERROR),
	E_UNSUPPORTED_BIT_WIDTH(ERROR),
	E_NUMBER_CAN_NOT_BE_STORED_USING_GIVEN_BIT_WIDTH(ERROR,
			"The given number does not fit the specified storage size."),

	E_ILLEGAL_CHARACTER_ESCAPE_V(ERROR,
			"The character escape \\v is not supported. Use \\x0B instead"
					+ " and Stop the Vertical Tab Madness!"),
	W_UNKNOWN_CHARACTER_ESCAPE(WARNING),
	W_INVALID_CHARACTER_ESCAPE_SEQUENCE_X(WARNING),
	W_INVALID_CHARACTER_ESCAPE_SEQUENCE_U(WARNING),
	W_INVALID_CHARACTER_ESCAPE_SEQUENCE_CX(WARNING),
	W_INVALID_CHARACTER_ESCAPE_SEQUENCE_CU(WARNING),
	W_CHARACTER_MAY_ONLY_BE_A_SINGLE_CHARACTER(WARNING),
	E_NON_ASCII_CHARACTER_IN_BINARY_STRING(ERROR),

	E_EXCEPTION_WHILE_PROCESSING_TOKEN(
			ERROR,
			"An exception occured while processing the token. The exception"
					+ " was caught by the Tokenizer but was not properly handled"
					+ " by the corresponding token processor. The token"
					+ " is probably malformed (which is what caused this exception).");

	final MessageType type;
	final String message;

	TokenizerMessage(MessageType type) {
		this.type = type;
		this.message = "";
	}

	TokenizerMessage(MessageType type, String message) {
		this.type = type;
		this.message = message;
	}

	@Override
	public MessageType getMessageType() {
		return type;
	}

	@Override
	public String getMessage() {
		if (message.isEmpty()) {
			return name();
		}
		return name() + "\n\n" + message;
	}

}
