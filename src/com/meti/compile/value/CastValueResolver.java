package com.meti.compile.value;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;

public class CastValueResolver implements ValueResolver {
	@Override
	public boolean canResolveValue(String value) {
		return value.startsWith("(") && value.contains(")");
	}

	@Override
	public Type resolveValue(String value, Compiler compiler) {
		int end = value.indexOf(')');
		return compiler.resolveName(value.substring(1, end).trim());
	}
}
