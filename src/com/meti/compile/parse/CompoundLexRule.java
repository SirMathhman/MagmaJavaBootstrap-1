package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundLexRule implements LexRule {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return supplyRules().stream()
				.map(rule -> rule.parse(content, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}

	public abstract Collection<LexRule> supplyRules();
}
