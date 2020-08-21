package com.meti;

public class FunctionNode implements Node {
	private final String name;
	private final Type returnType;
	private final String value;

	public FunctionNode(String name, Type returnType, String value) {
		this.name = name;
		this.returnType = returnType;
		this.value = value;
	}

	public String render() {
		return getReturnType().render(getName() + "()" + getValue());
	}

	public Type getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
