package com.meti.compile.node.block;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.List;

public class FunctionNodeBuilder {
	private String name;
	private List<Field> parameters;
	private Type returnType;
	private Node value;

	public FunctionNode build() {
		return new FunctionNode(name, returnType, value, parameters);
	}

	public FunctionNodeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public FunctionNodeBuilder withReturnType(Type returnType) {
		this.returnType = returnType;
		return this;
	}

	public FunctionNodeBuilder withParameters(List<Field> parameters) {
		this.parameters = parameters;
		return this;
	}

	public FunctionNodeBuilder withValue(Node value) {
		this.value = value;
		return this;
	}
}