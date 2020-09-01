package com.meti.compile.type;

import com.meti.compile.process.util.CallFlag;
import com.meti.compile.process.util.Declaration;
import com.meti.compile.process.util.MapDeclaration;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class InlineField implements Field {
	private final Type type;
	private final String name;
	private final List<CallFlag> flags;

	public InlineField(String name, Type type, List<CallFlag> flags) {
		this.name = name;
		this.type = type;
		this.flags = flags;
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
	public Field withName(String name) {
		return new InlineField(name, type, flags);
	}

    @Override
    public <T> T apply(Function<Field, T> function) {
        return function.apply(this);
    }

	@Override
	public Field acceptType(Consumer<Type> consumer) {
		consumer.accept(type);
		return this;
	}

	@Override
	public Declaration createDeclaration(String nameToUse){
		return new MapDeclaration(nameToUse, flags);
	}
}
