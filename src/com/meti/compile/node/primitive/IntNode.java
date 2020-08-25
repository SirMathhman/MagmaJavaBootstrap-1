package com.meti.compile.node.primitive;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.function.Function;

public class IntNode implements Node {
	private final int value;

	public IntNode(int value) {
		this.value = value;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(new EmptyDependents());
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Int);
	}

	@Override
	public Node copy(Dependents dependents) {
		return new IntNode(value);
	}

	@Override
	public String render() {
		return String.valueOf(value);
	}
}
