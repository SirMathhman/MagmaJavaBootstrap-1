package com.meti.compile.node.scope;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public class DeclareNode implements Node {
	private final String name;
	private final Type type;

	public DeclareNode(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String render() {
		return "%s;".formatted(type.render(name));
	}
}
