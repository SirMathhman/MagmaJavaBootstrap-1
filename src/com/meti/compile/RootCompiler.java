package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.parse.LexRule;
import com.meti.compile.parse.ParseException;
import com.meti.compile.resolve.ResolveException;
import com.meti.compile.resolve.ResolveRule;
import com.meti.compile.type.Type;

public class RootCompiler implements Compiler {
	private final ResolveRule rootResolveRule;
	private final LexRule rootLexRule;

	public RootCompiler(LexRule rootLexRule, ResolveRule rootResolveRule) {
		this.rootResolveRule = rootResolveRule;
		this.rootLexRule = rootLexRule;
	}

	@Override
	public Node parse(String content) {
		return rootLexRule.parse(content, this)
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
		String message = "Failed to resolve: %s".formatted(name);
		return new ResolveException(message);
	}
}