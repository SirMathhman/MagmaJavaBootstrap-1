package com.meti.compile.node;

import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Dependents {
	<T> T apply(BiFunction<List<TypePair>, List<Node>, T> function);

	<T> T applyToChildren(Function<List<Node>, T> mapper);

	<T> T applyToFields(Function<List<TypePair>, T> pairs);

	Dependents copyChildren(List<Node> children);
}
