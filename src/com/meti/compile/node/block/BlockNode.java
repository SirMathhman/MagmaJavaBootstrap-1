package com.meti.compile.node.block;

import com.meti.compile.node.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class BlockNode implements Node {
	private final Collection<Node> nodes;

	public BlockNode(Collection<Node> nodes) {
		this.nodes = Collections.unmodifiableCollection(nodes);
	}

	@Override
	public String render() {
		return nodes.stream()
				.map(Node::render)
				.collect(Collectors.joining("", "{", "}"));
	}
}
