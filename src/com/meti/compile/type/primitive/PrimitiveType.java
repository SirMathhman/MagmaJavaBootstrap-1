package com.meti.compile.type.primitive;

import com.meti.compile.type.Type;

import java.util.stream.Stream;

public enum PrimitiveType implements Type {
	Int("int"),
	Implicit("?"),
	Char("char"),
	Unknown("?");

	private final String name;

	PrimitiveType(String name) {
		this.name = name;
	}

	@Override
	public String render(String name) {
		return this.name + " " + name;
	}

	@Override
	public Stream<Type> streamChildren() {
		return Stream.empty();
	}
}
