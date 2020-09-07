package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Token;

import java.util.Optional;

public abstract class FilteredLexRule implements LexRule {
	@Override
	public Optional<Token> parse(String content, Lexer lexer) {
		return Optional.of(content)
				.filter(this::canQualify)
				.map(value -> parseQualified(value, lexer));
	}

	public abstract boolean canQualify(String content);

	public abstract Token parseQualified(String content, Lexer lexer);
}
