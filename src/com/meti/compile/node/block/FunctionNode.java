package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Type;

import java.util.function.Function;

public class FunctionNode implements Node {
	private final String name;
	private final Type returnType;
	private final Node value;

	public FunctionNode(String name, Type returnType, Node value) {
		this.name = name;
		this.returnType = returnType;
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
		String renderedValue = value.render();
		return returnType.render(name + "()" + renderedValue);
	}
}
