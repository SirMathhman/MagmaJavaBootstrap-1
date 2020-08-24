package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockNode implements Node {
	private final Collection<Node> nodes;

	public BlockNode(Collection<Node> nodes) {
		this.nodes = Collections.unmodifiableCollection(nodes);
	}

	@Override
	public Node copy(Dependents dependents) {
		return null;
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
		return nodes.stream()
				.map(Node::render)
				.collect(Collectors.joining("", "{", "}"));
	}
}
