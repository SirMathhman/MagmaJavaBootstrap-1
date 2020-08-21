package com.meti;

public enum PrimitiveType implements Type {
	Int("int");
	private final String name;

	PrimitiveType(String name) {
		this.name = name;
	}

	@Override
	public String render(String name) {
		return this.name + " " + name;
	}
}
