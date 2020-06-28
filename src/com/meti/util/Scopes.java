package com.meti.util;

import com.meti.compile.type.Type;

import java.util.Optional;

public interface Scopes {
	void define(String name, Type type);

	void enter();

	void exit();

	Optional<Type> lookup(String name);

	boolean isDefined(String name);
}
