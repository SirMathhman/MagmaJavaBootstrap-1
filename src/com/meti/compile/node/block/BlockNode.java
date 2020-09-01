package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockNode extends ParentNode {
	private final List<Node> children;

	public BlockNode(List<Node> children) {
		this.children = Collections.unmodifiableList(children);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Block);
	}

	@Override
	public Dependents createDependents() {
		return InlineDependents.ofChildren(children);
	}

	@Override
	public Node copy(Dependents dependents) {
		return dependents.streamChildren()
				.reduce(new BlockNodeBuilder(),
						BlockNodeBuilder::append,
						(oldBuilder, newBuilder) -> newBuilder)
				.build();
	}

	@Override
	public String render() {
		return children.stream()
				.map(Node::render)
				.collect(Collectors.joining("", "{", "}"));
	}
}
