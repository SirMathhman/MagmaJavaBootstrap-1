package com.meti.compile.node.block;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.List;

public class FunctionNodeBuilder {
	private String name;
	private List<TypePair> parameters;
	private Type returnType;
	private Node value;

	public FunctionNode build() {
		return new FunctionNode(name, returnType, value, parameters);
	}

	public FunctionNodeBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public FunctionNodeBuilder setReturnType(Type returnType) {
		this.returnType = returnType;
		return this;
	}

	public FunctionNodeBuilder withParameters(List<TypePair> parameters) {
		this.parameters = parameters;
		return this;
	}

	public FunctionNodeBuilder withValue(Node value) {
		this.value = value;
		return this;
	}
}