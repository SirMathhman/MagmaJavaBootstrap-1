package com.meti.compile.node.scope;

import com.meti.compile.node.Node;

public class VariableNode implements Node {
	private final String content;

	public VariableNode(String content) {
		this.content = content;
	}

	@Override
	public String render() {
		return content;
	}
}
