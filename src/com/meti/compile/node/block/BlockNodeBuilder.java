package com.meti.compile.node.block;

import com.meti.compile.node.Node;

import java.util.ArrayList;
import java.util.List;

public class BlockNodeBuilder {
	private final List<Node> children = new ArrayList<>();

	public BlockNodeBuilder append(Node node) {
		children.add(node);
		return this;
	}

	public Node build() {
		return new BlockNode(children);
	}
}
