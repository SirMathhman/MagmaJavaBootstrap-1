package com.meti.compile.type;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class InlineTypePair implements TypePair {
	private final Type type;
	private final String name;

	public InlineTypePair(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public <T> T apply(BiFunction<String, Type, T> function) {
		return function.apply(name, type);
	}

	@Override
	public <T> T applyToName(Function<String, T> function) {
		return function.apply(name);
	}

	@Override
	public <T> T applyToType(Function<Type, T> function) {
		return function.apply(type);
	}

	@Override
	public String render() {
		return type.render(name);
	}

	@Override
	public TypePair withName(String name) {
		return new InlineTypePair(name, type);
	}

    @Override
    public <T> T apply(Function<TypePair, T> function) {
        return function.apply(this);
    }

	@Override
	public TypePair acceptType(Consumer<Type> consumer) {
		throw new UnsupportedOperationException();
	}
}
