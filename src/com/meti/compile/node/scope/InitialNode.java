package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Type;

import java.util.function.Function;

public class InitialNode implements Node {
	private final String name;
	private final Type type;
	private final Node value;

	public InitialNode(String name, Type type, Node value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	@Override
	public Node copy(Dependents dependents) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(null);
	}

	@Override
	public String render() {
		String renderedType = type.render(name);
		String renderedValue = value.render();
		return "%s=%s;".formatted(renderedType, renderedValue);
	}
}
