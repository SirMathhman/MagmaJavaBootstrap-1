package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.*;

import static java.util.Collections.*;

public class DeclareNode implements Node {
	private final Node initial;
	private final String name;
	private final Type type;

	public DeclareNode(String name, Type type) {
		this(name, type, null);
	}

	public DeclareNode(String name, Type type, Node initial) {
		this.type = type;
		this.initial = initial;
		this.name = name;
	}

	@Override
	public Collection<Node> getChildren() {
		return null == initial ? emptyList() : singletonList(initial);
	}

	@Override
	public Node copy(Map<String, ? extends Type> fields, List<? extends Node> children) {
		List<String> fieldNames = new ArrayList<>(fields.keySet());
		if(fieldNames.isEmpty()) throw new IllegalArgumentException("Not enough getFields.");
		if (1 != fieldNames.size()) throw new IllegalArgumentException("Too many getFields.");
		String fieldName = fieldNames.get(0);
		Type fieldType = fields.get(fieldName);
		Node initialNode = children.isEmpty() ? null : children.get(0);
		return new DeclareNode(fieldName, fieldType, initialNode);
	}

	@Override
	public <T> Optional<T> getContent(Class<T> clazz) {
		return Optional.empty();
	}

	@Override
	public Map<String, Type> getFields() {
		return singletonMap(name, type);
	}

	@Override
	public Optional<String> getValue() {
		return Optional.empty();
	}

	@Override
	public boolean isParsed() {
		return type.isParsed() && (null == initial || initial.isParsed());
	}

}
