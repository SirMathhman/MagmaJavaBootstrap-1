package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

import java.util.Optional;

public abstract class FilteredParseRule implements ParseRule {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.filter(this::canQualify)
				.map(value -> parseQualified(value, compiler));
	}

	public abstract boolean canQualify(String content);

	public abstract Node parseQualified(String content, Compiler compiler);
}
