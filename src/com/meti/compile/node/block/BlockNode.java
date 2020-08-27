package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockNode implements Node {
	private final List<Node> children;

	public BlockNode(List<Node> children) {
		this.children = Collections.unmodifiableList(children);
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		consumer.accept(InlineDependents.ofChildren(children));
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(InlineDependents.ofChildren(children));
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Block);
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
