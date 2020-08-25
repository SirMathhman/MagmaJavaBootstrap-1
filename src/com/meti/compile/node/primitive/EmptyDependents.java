package com.meti.compile.node.primitive;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.type.TypePair;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class EmptyDependents implements Dependents {
	@Override
	public <T> T apply(BiFunction<List<TypePair>, List<Node>, T> function) {
		return function.apply(Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public Dependents copyChildren(List<Node> children) {
		return new EmptyDependents();
	}

	@Override
	public Stream<Node> streamChildren() {
		return Stream.empty();
	}

	@Override
	public Stream<TypePair> streamFields() {
		return Stream.empty();
	}
}
