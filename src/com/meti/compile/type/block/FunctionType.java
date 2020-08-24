package com.meti.compile.type.block;

import com.meti.compile.type.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionType implements Type {
	private final Collection<Type> parameterTypes;
	private final Type returnType;

	public FunctionType(Type returnType, Collection<Type> parameterTypes) {
		this.returnType = returnType;
		this.parameterTypes = Collections.unmodifiableCollection(parameterTypes);
	}

	@Override
	public String render(String name) {
		String parameterString = parameterTypes.stream()
				.map(Type::render)
				.collect(Collectors.joining(",", "(", ")"));
		String value = "(*%s)%s".formatted(name, parameterString);
		return returnType.render(value);
	}

	@Override
	public Stream<Type> streamChildren() {
		return Stream.empty();
	}
}
