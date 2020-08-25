package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.LexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.primitive.IntNode;

import java.util.Optional;

public class IntLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Lexer lexer) {
		try {
			int value = Integer.parseInt(content);
			return Optional.of(new IntNode(value));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
