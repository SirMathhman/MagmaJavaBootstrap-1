package com.meti.compile.value;

import com.meti.compile.Compiler;
import com.meti.compile.type.Type;
import com.meti.util.Scopes;

public class VariableValueResolver implements ValueResolver {
	private final Scopes scopes;

	public VariableValueResolver(Scopes scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean canResolveValue(String value) {
		return scopes.isDefined(value);
	}

	@Override
	public Type resolveValue(String value, Compiler compiler) {
		return scopes.lookup(value).orElseThrow();
	}
}
