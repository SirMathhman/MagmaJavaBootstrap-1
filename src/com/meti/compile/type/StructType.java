package com.meti.compile.type;

public final class StructType implements Type {
	private final String name;

	public StructType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String render(String name) {
		return "struct " + this.name + " " + name;
	}
}
