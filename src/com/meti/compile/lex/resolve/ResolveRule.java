package com.meti.compile.lex.resolve;

import com.meti.compile.type.Type;

import java.util.Optional;

public interface ResolveRule {
	Optional<Type> resolve(String name);
}
