package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.*;

public class BlockNode implements Node {
	private final Collection<Node> children;

	public BlockNode(Node node) {
		this(Collections.singleton(node));
	}

	public BlockNode(Collection<? extends Node> children) {
		this.children = Collections.unmodifiableCollection(children);
	}

	@Override
	public Collection<Node> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	@Override
	public Node copy(Map<String, ? extends Type> fields, List<? extends Node> children) {
		return new BlockNode(children);
	}

	@Override
	public <T> Optional<T> getContent(Class<T> clazz) {
		return Optional.empty();
	}

	@Override
	public Map<String, Type> getFields() {
		return Collections.emptyMap();
	}

	@Override
	public Optional<String> getValue() {
		return Optional.empty();
	}

	@Override
	public boolean isParsed() {
		return children.stream().allMatch(Node::isParsed);
	}
}
