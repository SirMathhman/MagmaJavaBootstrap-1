package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.*;

public class StringNode implements Node {
	private final String value;

	public StringNode() {
		this("");
	}

	public StringNode(String value) {
		this.value = value;
	}

	@Override
	public Collection<Node> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public Node copy(Map<String, ? extends Type> fields, List<? extends Node> children) {
		throw new UnsupportedOperationException("Cannot copy a StrigNode");
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
		return Optional.of(value);
	}

	@Override
	public boolean isParsed() {
		return false;
	}

}
