package com.meti.compile.type.primitive;

import com.meti.compile.type.Type;

public enum PrimitiveType implements Type {
	Int("int"),
	Unknown("?"),
	Char("char");

	private final String name;

	PrimitiveType(String name) {
		this.name = name;
	}

	@Override
	public String render(String name) {
		return this.name + " " + name;
	}
}
