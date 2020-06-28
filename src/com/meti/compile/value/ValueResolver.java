package com.meti.compile.value;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;

public interface ValueResolver {
	boolean canResolveValue(String value);

	Type resolveValue(String value, Compiler compiler);
}
