package com.meti.compile.node.block;

import com.meti.compile.node.Node;

public class ReturnNode implements Node {
	private final Node value;

	public ReturnNode(Node value) {
		this.value = value;
	}

	@Override
	public String render() {
		String renderedValue = value.render();
		return "return %s;".formatted(renderedValue);
	}
}
