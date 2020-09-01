package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Lexer lexer) {
		return supplyRules().stream()
				.map(rule -> rule.parse(content, lexer))
				.flatMap(Optional::stream)
				.findFirst();
	}

	public abstract Collection<LexRule> supplyRules();
}
