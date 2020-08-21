package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

import java.util.Optional;

public interface FilteredParseRule extends ParseRule {
	@Override
	default Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.filter(this::canQualify)
				.map(value -> parseQualified(value, compiler));
	}

	boolean canQualify(String content);

	Node parseQualified(String content, Compiler compiler);
}
