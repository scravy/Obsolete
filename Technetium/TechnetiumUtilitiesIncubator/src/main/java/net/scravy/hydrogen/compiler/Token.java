package net.scravy.hydrogen.compiler;

import java.io.File;

public class Token {

	TokenType type;
	private int pos = -1;

	final int beginIndex;
	final int endIndex;
	final int line;
	final String string;
	final File source;

	Object value = null;

	Token(String string, TokenType type, File source,
			int line, int beginIndex, int endIndex) {
		this.source = source;
		this.string = string;
		this.type = type;
		this.line = line;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex > string.length() ? endIndex - 1
				: endIndex;
	}

	Message process(Tokenizer2 tokenizer) {
		if (type instanceof TokenTypeInternal) {
			return ((TokenTypeInternal) type).process(tokenizer, this);
		}
		return null;
	}

	public int getPos() {
		if (this.pos < 0) {
			pos = 0;
			for (int i = beginIndex; i >= 0; i--) {
				if (string.charAt(i) == '\n') {
					break;
				}
				pos++;
			}
		}
		return pos;
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	public int getLine() {
		return line;
	}

	public TokenType getType() {
		return type;
	}

	void setType(TokenType type) {
		this.type = type;
	}

	void setValue(Object value) {
		this.value = value;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public int getLength() {
		return endIndex - beginIndex;
	}

	public Object getValue() {
		return value == null
				? string.substring(beginIndex, endIndex)
				: value;
	}

	public String getDeclaredValue() {
		return string.substring(beginIndex, endIndex);
	}

	public String getDeclaredValue(int leftOffset, int rightOffset) {
		return string
				.substring(beginIndex + leftOffset, endIndex + rightOffset);
	}

}