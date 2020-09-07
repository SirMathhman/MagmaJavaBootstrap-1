package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.node.Token;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundLexRule implements LexRule {
	@Override
	public Optional<Token> parse(String content, Lexer lexer) {
		return supplyRules().stream()
				.map(rule -> rule.parse(content, lexer))
				.flatMap(Optional::stream)
				.findFirst();
	}

	public abstract Collection<LexRule> supplyRules();

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
