package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.parse.ParseException;
import com.meti.compile.parse.ParseRule;
import com.meti.compile.resolve.NameRule;
import com.meti.compile.resolve.ResolveException;
import com.meti.compile.type.Type;

public class RootCompiler implements Compiler {
	private final NameRule rootNameRule;
	private final ParseRule rootParseRule;

	public RootCompiler(ParseRule rootParseRule, NameRule rootNameRule) {
		this.rootNameRule = rootNameRule;
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
		return rootNameRule.resolve(name)
				.orElseThrow(() -> createInvalidResolve(name));
	}

	public static ResolveException createInvalidResolve(String name) {
		String message = "Failed to resolve: %s".formatted(name);
		return new ResolveException(message);
	}
}