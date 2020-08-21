package com.meti;

import java.util.Optional;

public interface FilteredNameRule extends NameRule {
	@Override
	default Optional<Type> resolve(String name) {
		return Optional.of(name)
				.filter(this::canResolve)
				.map(this::resolveImpl);
	}

	boolean canResolve(String name);

	Type resolveImpl(String name);
}
