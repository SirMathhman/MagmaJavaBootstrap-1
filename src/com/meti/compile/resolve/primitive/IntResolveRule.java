package com.meti.compile.resolve.primitive;

import com.meti.compile.resolve.FilteredResolveRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class IntResolveRule implements FilteredResolveRule {
	@Override
	public boolean canResolve(String name) {
		return "Int".equals(name);
	}

	@Override
	public Type resolveImpl(String name) {
		return PrimitiveType.Int;
	}
}