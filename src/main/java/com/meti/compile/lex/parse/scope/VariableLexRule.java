package com.meti.compile.lex.parse.scope;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.LexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.scope.VariableNode;

import java.util.Optional;

public class VariableLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Lexer lexer) {
		return Optional.of(new VariableNode(content));
	}
}
