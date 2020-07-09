package com.meti.compile.parse;

import com.meti.compile.node.Node;
import com.meti.compile.node.VariableNode;

public class VariableNodeStage implements NodeStage {
	@Override
	public boolean canAccept(Node node) {
		return node.getValue().isPresent();
	}

	@Override
	public Node accept(Node node) {
		return new VariableNode(node.getValue().orElseThrow());
	}
}
