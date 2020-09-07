package com.meti.compile.node.block;

import com.meti.compile.node.Token;

import java.util.ArrayList;
import java.util.List;

public class BlockNodeBuilder {
	private final List<Token> children = new ArrayList<>();

	public BlockNodeBuilder append(Token token) {
		children.add(token);
		return this;
	}

	public Token build() {
		return new BlockToken(children);
	}
}
