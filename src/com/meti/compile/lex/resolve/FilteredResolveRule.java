package com.meti.compile.lex.resolve;

import com.meti.compile.type.Type;

import java.util.Optional;

public abstract class FilteredResolveRule implements ResolveRule {
	@Override
	public Optional<Type> resolve(String name) {
		return Optional.of(name)
				.filter(this::canResolve)
				.map(this::resolveImpl);
	}

	public abstract boolean canResolve(String name);

	public abstract Type resolveImpl(String name);
}
