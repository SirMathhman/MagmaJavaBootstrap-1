package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.parse.ParseException;
import com.meti.compile.parse.ParseRule;
import com.meti.compile.resolve.ResolveException;
import com.meti.compile.resolve.ResolveRule;
import com.meti.compile.type.Type;

public class RootCompiler implements Compiler {
	private final ResolveRule rootResolveRule;
	private final ParseRule rootParseRule;

	public RootCompiler(ParseRule rootParseRule, ResolveRule rootResolveRule) {
		this.rootResolveRule = rootResolveRule;
		this.rootParseRule = rootParseRule;
	}

	@Override
	public Node parse(String content) {
		return rootParseRule.parse(content, this)
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