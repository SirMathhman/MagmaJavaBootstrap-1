package com.meti;

public class ReturnNode implements Node {
	private final String value;

	public ReturnNode(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return "return " + getValue();
	}

	public String getValue() {
		return value;
	}
}
