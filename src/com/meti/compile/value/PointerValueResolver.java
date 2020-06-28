package com.meti.compile.value;

import com.meti.MagmaException;
import com.meti.compile.Compiler;
import com.meti.compile.type.PointerType;
import com.meti.compile.type.Type;

public class PointerValueResolver implements ValueResolver {
	@Override
	public boolean canResolveValue(String value) {
		return value.startsWith("*");
	}

	@Override
	public Type resolveValue(String value, Compiler compiler) {
		String content = value.substring(1).trim();
		Type type = compiler.resolveValue(content);
		if(type instanceof PointerType) {
			return ((PointerType) type).getParent();
		} else {
			throw new MagmaException(content + " does not have type of pointer.");
		}
	}
}
