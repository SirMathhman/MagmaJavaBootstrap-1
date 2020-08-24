package com.meti.compile.node;

import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.function.Function;

public interface Dependents {
	<T> T applyToFields(Function<List<TypePair>, T> pairs);

	Dependents copyChildren(List<Node> children);

	<T> T mapChildren(Function<List<Node>, T> mapper);
}
