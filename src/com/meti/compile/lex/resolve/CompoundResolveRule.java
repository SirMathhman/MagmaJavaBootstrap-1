package com.meti.compile.lex.resolve;

import com.meti.compile.type.Type;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundResolveRule implements ResolveRule {
	@Override
	public Optional<Type> resolve(String name) {
		return supplyRules().stream()
				.map(rule -> rule.resolve(name))
				.flatMap(Optional::stream)
				.findFirst();
	}

	public abstract Collection<ResolveRule> supplyRules();
}
