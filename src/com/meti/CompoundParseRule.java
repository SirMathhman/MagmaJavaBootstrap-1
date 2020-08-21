package com.meti;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundParseRule implements ParseRule {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return supplyRules().stream()
				.map(rule -> rule.parse(content, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}

	public abstract Collection<ParseRule> supplyRules();
}
