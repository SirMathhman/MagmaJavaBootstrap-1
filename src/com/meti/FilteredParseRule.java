package com.meti;

import java.util.Optional;

public interface FilteredParseRule extends ParseRule {
	@Override
	default Optional<Node> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.filter(this::canParse)
				.map(value -> parseImpl(value, compiler));
	}

	boolean canParse(String content);

	Node parseImpl(String content, Compiler compiler);
}
