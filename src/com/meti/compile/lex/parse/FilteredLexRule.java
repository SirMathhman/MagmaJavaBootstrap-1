package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;

import java.util.Optional;

public abstract class FilteredLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Lexer lexer) {
		return Optional.of(content)
				.filter(this::canQualify)
				.map(value -> parseQualified(value, lexer));
	}

	public abstract boolean canQualify(String content);

	public abstract Node parseQualified(String content, Lexer lexer);
}
