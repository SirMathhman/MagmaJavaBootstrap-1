package com.meti.compile.name;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;

public interface NameResolver {
	boolean canResolve(String name);

	Type resolveName(String name, Compiler compiler);
}
