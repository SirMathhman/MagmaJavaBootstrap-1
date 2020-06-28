package com.meti.compile.value;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;

public class QuantityValueResolver implements ValueResolver {
	@Override
	public boolean canResolveValue(String value) {
		return value.startsWith("(") && value.endsWith(")");
	}

	@Override
	public Type resolveValue(String value, Compiler compiler) {
		String substring = value.substring(1, value.length() - 1);
		return compiler.resolveValue(substring);
	}
}
