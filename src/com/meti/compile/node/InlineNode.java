package com.meti.compile.node;

public class InlineNode implements Node {
	private final String value;

	public InlineNode(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return value;
	}
}
