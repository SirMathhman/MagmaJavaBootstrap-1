package com.meti.compile.name;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;

public class AnyNameResolver implements NameResolver {
	@Override
	public boolean canResolve(String name) {
		return "any".equals(name);
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		return new AnyType();
	}

	private static final class AnyType implements Type {
		@Override
		public String render(String name) {
			return "void " + name;
		}
	}
}
