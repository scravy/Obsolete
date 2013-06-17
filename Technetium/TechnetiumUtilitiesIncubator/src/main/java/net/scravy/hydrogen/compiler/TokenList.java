package net.scravy.hydrogen.compiler;


class TokenList {

	class TokenLink {
		TokenList.TokenLink prev;
		TokenList.TokenLink next;
		Token tok;

		TokenLink(Token tok) {
			size++;
			this.tok = tok;
		}

		TokenType typeOfPrevious() {
			if (prev == null) {
				return BasicTokenType.T_WHITE_SPACE;
			}
			return prev.tok.type;
		}

		TokenType typeOfNext() {
			if (next == null) {
				return BasicTokenType.T_WHITE_SPACE;
			}
			return next.tok.type;
		}

		TokenLink replace(Token tok) {
			this.tok = tok;
			return this;
		}

		TokenLink replace(Token... tokens) {
			TokenLink newLink = null;
			for (Token tok : tokens) {
				newLink = insertBefore(tok);
			}
			remove();
			return newLink;
		}

		void replace(TokenTypeInternal type) {

		}

		TokenLink insertBefore(Token tok) {
			TokenList.TokenLink newLink = new TokenLink(tok);
			newLink.next = this;
			if (prev == null) {
				first = newLink;
			} else {
				prev.next = newLink;
				newLink.prev = prev;
			}
			prev = newLink;
			return newLink;
		}

		TokenLink insertAfter(Token tok) {
			TokenList.TokenLink newLink = new TokenLink(tok);
			newLink.prev = this;
			if (next == null) {
				last = newLink;
			} else {
				next.prev = newLink;
				newLink.next = next;
			}
			next = newLink;
			return newLink;
		}

		void remove() {
			if (prev == null) {
				first = next;
				if (next == null) {
					last = null;
				}
			} else {
				prev.next = next;
			}
			if (next == null) {
				last = prev;
				if (prev == null) {
					first = null;
				}
			} else {
				next.prev = prev;
			}
			size--;
		}
	}

	int size = 0;
	TokenLink first = null;
	TokenLink last = null;

	public void add(Token tok) {
		if (first == null) {
			first = new TokenLink(tok);
			last = first;
		} else {
			last.insertAfter(tok);
		}
	}

	public boolean isEmpty() {
		return first == null;
	}
}