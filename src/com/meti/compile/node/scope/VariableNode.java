package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.function.Function;

import static com.meti.compile.node.EmptyDependents.EmptyDependents;

public class VariableNode implements Node {
	private final String content;

	public VariableNode(String content) {
		this.content = content;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(EmptyDependents());
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Variable);
	}

	@Override
	public Node copy(Dependents dependents) {
		return new VariableNode(content);
	}

	@Override
	public String render() {
		return content;
	}
}
