package com.meti.compile.node.primitive;

import com.meti.compile.node.Node;

public class CharNode implements Node {
	private final char value;

	public CharNode(char value) {
		this.value = value;
	}

	@Override
	public String render() {
		return "'%s'".formatted(value);
	}
}
