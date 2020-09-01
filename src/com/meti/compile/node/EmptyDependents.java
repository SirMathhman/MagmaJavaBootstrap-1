package com.meti.compile.node;

import com.meti.compile.type.Field;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class EmptyDependents implements Dependents {
	public static final EmptyDependents THIS = new EmptyDependents();

	private EmptyDependents() {
	}

	@Override
	public <T> T apply(BiFunction<List<Field>, List<Node>, T> function) {
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
	public Dependents copyFields(List<Field> fields) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<Node> streamChildren() {
		return Stream.empty();
	}

	@Override
	public Stream<Field> streamFields() {
		return Stream.empty();
	}
}
