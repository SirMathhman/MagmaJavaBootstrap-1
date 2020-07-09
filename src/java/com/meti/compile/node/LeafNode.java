package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.*;

public class LeafNode implements Node {
	@Override
	public Node copy(Map<String, ? extends Type> fields, List<? extends Node> children) {
		return this;
	}

	@Override
	public Collection<Node> getChildren() {
		return Collections.emptyList();
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
		return true;
	}
}
