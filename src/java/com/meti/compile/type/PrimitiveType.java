package com.meti.compile.type;

public enum PrimitiveType implements Type {
	INT("Int"),
	UNKNOWN("?");

	private final String value;

	PrimitiveType(String value) {
		this.value = value;
	}

	@Override
	public String render(String name) {
		return value + " " + name;
	}

	@Override
	public boolean isParsed() {
		return true;
	}
}
