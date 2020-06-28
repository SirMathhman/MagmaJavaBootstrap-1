package com.meti.compile.name;

import com.meti.compile.Compiler;
import com.meti.compile.type.InlineType;
import com.meti.compile.type.Type;

public class CharNameResolver implements NameResolver {
	@Override
	public boolean canResolve(String name) {
		return "char".equals(name);
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		return new InlineType("char");
	}
}
