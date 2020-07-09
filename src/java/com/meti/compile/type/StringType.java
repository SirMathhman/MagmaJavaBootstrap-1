package com.meti.compile.type;

import com.meti.compile.node.StringNode;

public class StringType extends StringNode implements Type {
	public StringType(String value) {
		super(value);
	}

	@Override
	public String render(String name) {
		throw new UnsupportedOperationException("Cannot render StringType");
	}
}
