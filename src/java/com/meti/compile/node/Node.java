package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Node {
	Node copy(Map<String, ? extends Type> fields, List<? extends Node> children);

	Collection<Node> getChildren();

	<T> Optional<T> getContent(Class<T> clazz);

	Map<String, Type> getFields();

	Optional<String> getValue();

	boolean isParsed();
}
