package com.meti;

public class IntNameRule implements FilteredNameRule {
	@Override
	public boolean canResolve(String name) {
		return "Int".equals(name);
	}

	@Override
	public Type resolveImpl(String name) {
		return PrimitiveType.Int;
	}
}