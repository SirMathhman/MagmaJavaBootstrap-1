package com.meti.util;

import com.meti.compile.type.Type;

import java.util.Optional;

public interface Scope {
	void define(String name, Type type);

	Scope enter();

	Optional<Scope> exit();

	Optional<Type> getDefinition(String name);

	boolean isDefined(String name);
}
