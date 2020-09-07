package com.meti.compile.lex;

import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.lex.parse.ParseException;
import com.meti.compile.lex.resolve.ResolveException;
import com.meti.compile.lex.resolve.ResolveRule;
import com.meti.compile.node.Token;
import com.meti.compile.type.Type;

public class RootLexer implements Lexer {
	private final ResolveRule rootResolveRule;
	private final TokenizerFactory rootTokenizerFactory;

	public RootLexer(TokenizerFactory rootTokenizerFactory, ResolveRule rootResolveRule) {
		this.rootResolveRule = rootResolveRule;
		this.rootTokenizerFactory = rootTokenizerFactory;
	}

	@Override
	public Token parse(String content) {
		return rootTokenizerFactory.parse(content, this)
				.orElseThrow(() -> createInvalidParse(content));
	}

	public static ParseException createInvalidParse(String content) {
		String message = "Failed to parse: %s".formatted(content);
		return new ParseException(message);
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
}