package com.meti.compile.type;

import java.util.function.BiFunction;

public class InlineTypePair implements TypePair {
	private final Type type;
	private final String value;

	public InlineTypePair(String value, Type type) {
		this.value = value;
		this.type = type;
	}

	@Override
	public <T> T map(BiFunction<String, Type, T> function) {
		return function.apply(value, type);
	}

	@Override
	public String render() {
		return type.render(value);
	}
}
