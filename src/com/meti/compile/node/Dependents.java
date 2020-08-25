package com.meti.compile.node;

import com.meti.compile.type.TypePair;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface Dependents {
	<T> T apply(BiFunction<List<TypePair>, List<Node>, T> function);

	Dependents copyChildren(List<Node> children);

	Stream<Node> streamChildren();

	Stream<TypePair> streamFields();
}
