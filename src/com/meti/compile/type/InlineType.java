package com.meti.compile.type;

public class InlineType implements Type {
	private final String value;

	public InlineType(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return value;
	}

	@Override
	public String render(String name) {
		return value + " " + name;
	}
}
