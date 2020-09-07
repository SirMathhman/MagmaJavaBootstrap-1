package com.meti.compile.lex;

import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.lex.resolve.ResolveException;
import com.meti.compile.lex.resolve.ResolveRule;
import com.meti.compile.node.Token;
import com.meti.compile.type.Type;
import com.meti.util.MonadOption;

public class RootLexer implements Lexer {
	private final ResolveRule rootResolveRule;
	private final TokenizerFactory rootTokenizerFactory;

	public RootLexer(TokenizerFactory rootTokenizerFactory, ResolveRule rootResolveRule) {
		this.rootResolveRule = rootResolveRule;
		this.rootTokenizerFactory = rootTokenizerFactory;
	}

	@Override
	public Token parse(String content) {
		throw new UnsupportedOperationException("Method has been deprecated.");
	}

	@Override
	public Type resolve(String name) {
		return rootResolveRule.resolve(name)
				.orElseThrow(() -> createInvalidResolve(name));
	}

	public static ResolveException createInvalidResolve(String name) {
		String message = "Failed to resolve name: %s".formatted(name);
		return new ResolveException(message);
	}

	@Override
	public MonadOption<Token> parseToOption(String content) {
		return rootTokenizerFactory.create(content).evaluate();
	}
}