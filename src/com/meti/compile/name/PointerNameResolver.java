package com.meti.compile.name;

import com.meti.compile.Compiler;
import com.meti.compile.type.PointerType;
import com.meti.compile.type.Type;

public class PointerNameResolver implements NameResolver {
	@Override
	public boolean canResolve(String name) {
		return name.endsWith("*");
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		String value = name.substring(0, name.length() - 1);
		Type type = compiler.resolveName(value);
		return new PointerType(type);
	}

}
