package com.meti.compile.lex.resolve.primitive;

import com.meti.compile.lex.resolve.FilteredResolveRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class IntResolveRule extends FilteredResolveRule {
	@Override
	public boolean canResolve(String name) {
		return "Int".equals(name);
	}

	@Override
	public Type resolveImpl(String name) {
		return PrimitiveType.Int;
	}
}