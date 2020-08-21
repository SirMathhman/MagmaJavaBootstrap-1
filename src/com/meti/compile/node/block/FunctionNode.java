package com.meti.compile.node.block;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public class FunctionNode implements Node {
	private final String name;
	private final Type returnType;
	private final Node value;

	public FunctionNode(String name, Type returnType, Node value) {
		this.name = name;
		this.returnType = returnType;
		this.value = value;
	}

	@Override
	public String render() {
		String renderedValue = value.render();
		return returnType.render(name + "()" + renderedValue);
	}
}
