package com.meti;

import java.util.Optional;

public interface NameRule {
	Optional<Type> resolve(String name);
}
