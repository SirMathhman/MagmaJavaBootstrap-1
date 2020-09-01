package com.meti.compile.node;

import com.meti.compile.type.Field;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface Dependents {
	<T> T apply(BiFunction<List<Field>, List<Node>, T> function);

	Dependents copyChildren(List<Node> children);

	Dependents copyFields(List<Field> fields);

	Stream<Node> streamChildren();

	Stream<Field> streamFields();
}
