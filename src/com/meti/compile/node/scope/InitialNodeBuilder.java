package com.meti.compile.node.scope;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public class InitialNodeBuilder {
	private String name;
	private Type type;
	private Node value;

	public InitialNode build() {
		return new InitialNode(name, type, value);
	}

	public InitialNodeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public InitialNodeBuilder withType(Type type) {
		this.type = type;
		return this;
	}

	public InitialNodeBuilder withValue(Node value) {
		this.value = value;
		return this;
	}
}