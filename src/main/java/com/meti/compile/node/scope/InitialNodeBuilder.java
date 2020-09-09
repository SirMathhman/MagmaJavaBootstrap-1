package com.meti.compile.node.scope;

import com.meti.compile.node.Token;
import com.meti.compile.instance.Type;

public class InitialNodeBuilder {
	private String name;
	private Type type;
	private Token value;

	public InitialToken build() {
		return new InitialToken(name, type, value);
	}

	public InitialNodeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public InitialNodeBuilder withType(Type type) {
		this.type = type;
		return this;
	}

	public InitialNodeBuilder withValue(Token value) {
		this.value = value;
		return this;
	}
}