package net.scravy.hydrogen.compiler;

import static net.scravy.hydrogen.compiler.BasicTokenType.*;
import static net.scravy.hydrogen.compiler.ParserTokenType.*;

import java.util.ArrayDeque;
import java.util.List;

class Parser1 {

	Parser1() {

	}

	Document parse(Compiler.Tokens tokens) {

		ArrayDeque<Container> blocks = new ArrayDeque<Container>();

		Document top = new Document(tokens.getProperties());
		blocks.push(top);

		Block firstBlock = new Block();
		top.add(firstBlock);
		blocks.push(firstBlock);

		ArrayDeque<CompoundToken> compounds = new ArrayDeque<CompoundToken>();

		Token macroInvocation = null;
		for (Token tok : tokens) {

			if (tok.type == T_MACRO_INCOVATION) {
				macroInvocation = tok;

			} else if (tok.type == T_OPENING_PARENTHESIS) {
				if (macroInvocation != null) {
					compounds.push(new CompoundToken(macroInvocation, C_MACRO_INVOCATION));
					macroInvocation = null;
				} else {
					compounds.push(new CompoundToken(tok, C_MOLECULE));
				}

			} else if (tok.type == T_OPENING_BRACKET) {
				compounds.push(new CompoundToken(tok, C_BRACKETS));

			} else if (tok.type == T_CLOSING_PARENTHESIS
					|| tok.type == T_CLOSING_BRACKET) {
				Token compound = compounds.pop();
				if (compounds.isEmpty()) {
					blocks.peek().add(compound);
				} else {
					compounds.peek().add(compound);
				}

			} else if (tok.type == T_BEGIN) {
				Block b = new Block();
				blocks.peek().add(b);
				blocks.push(b);

			} else if (tok.type == T_END) {
				if (blocks.pop().declaration.isEmpty()) {
					List<Block> peekBlocks = blocks.peek().blocks;
					peekBlocks.remove(peekBlocks.size() - 1);
				}
				blocks.pop();
				Block b = new Block();
				blocks.peek().add(b);
				blocks.push(b);

			} else if (tok.type == T_DELIMITER) {
				if (blocks.pop().declaration.isEmpty()) {
					List<Block> peekBlocks = blocks.peek().blocks;
					peekBlocks.remove(peekBlocks.size() - 1);
				}
				Block b = new Block();
				blocks.peek().add(b);
				blocks.push(b);

			} else {
				if (compounds.isEmpty()) {
					blocks.peek().add(tok);
				} else {
					compounds.peek().add(tok);
				}
			}
		}
		if (blocks.pop().declaration.isEmpty()) {
			List<Block> peekBlocks = blocks.peek().blocks;
			peekBlocks.remove(peekBlocks.size() - 1);
		}

		return top;
	}
}
