package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.function.Function;

public class VariableNode implements Node {
	private final String content;

	public VariableNode(String content) {
		this.content = content;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(null);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node copy(Dependents dependents) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String render() {
		return content;
	}
}
