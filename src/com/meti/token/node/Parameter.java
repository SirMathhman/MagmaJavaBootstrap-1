package com.meti.token.node;

import com.meti.token.type.Type;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Parameter implements ComplexNode {
	private final String name;
	private final Type type;

	public Parameter(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public List<Node> children() {
		return Collections.emptyList();
	}

	@Override
	public ComplexNode copy(List<Node> children) {
		return new Parameter(name, type);
	}

	@Override
	public ComplexNode copy(Map<String, Type> parameters) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node copyParameters(List<Node> parameters) {
		return this;
	}

	@Override
	public List<String> names() {
		return null;
	}

	@Override
	public List<Node> parameterNodes() {
		return Collections.emptyList();
	}

	@Override
	public Map<String, Type> parameters() {
		return null;
	}

	@Override
	public String render() {
		return null;
	}
}
