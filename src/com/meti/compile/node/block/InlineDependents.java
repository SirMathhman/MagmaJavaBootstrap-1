package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.type.TypePair;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

final class InlineDependents implements Dependents {
	private final List<Node> children;
	private final List<TypePair> fields;

	InlineDependents(List<TypePair> fields, List<Node> children) {
		this.fields = Collections.unmodifiableList(fields);
		this.children = Collections.unmodifiableList(children);
	}

	@Override
	public <T> T apply(BiFunction<List<TypePair>, List<Node>, T> function) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T applyToChildren(Function<List<Node>, T> mapper) {
		return mapper.apply(children);
	}

	@Override
	public <T> T applyToFields(Function<List<TypePair>, T> pairs) {
		return pairs.apply(fields);
	}

	@Override
	public Dependents copyChildren(List<Node> children) {
		return new InlineDependents(fields, children);
	}
}
