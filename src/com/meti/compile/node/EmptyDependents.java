package com.meti.compile.node;

import com.meti.compile.type.TypePair;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class EmptyDependents implements Dependents {
	public static final EmptyDependents THIS = new EmptyDependents();

	private EmptyDependents() {
	}

	@Override
	public <T> T apply(BiFunction<List<TypePair>, List<Node>, T> function) {
		return function.apply(Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public Dependents copyChildren(List<Node> children) {
		return EmptyDependents();
	}

	public static EmptyDependents EmptyDependents() {
		return THIS;
	}

	@Override
	public Dependents copyFields(List<TypePair> fields) {
		throw new UnsupportedOperationException();
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
