package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.function.Function;

public class ReturnNode implements Node {
	private final Node value;

	public ReturnNode(Node value) {
		this.value = value;
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
		String renderedValue = value.render();
		return "return %s;".formatted(renderedValue);
	}
}
