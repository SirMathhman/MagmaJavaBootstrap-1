package com.meti.compile.type;

import com.meti.compile.node.Node;

import java.util.*;

public interface Type extends Node {
	@Override
	default Collection<Node> getChildren() {
		return Collections.emptyList();
	}

	@Override
	default Node copy(Map<String, ? extends Type> fields, List<? extends Node> children) {
		return this;
	}

	@Override
	default <T> Optional<T> getContent(Class<T> clazz) {
		return Optional.empty();
	}

	@Override
	default Map<String, Type> getFields() {
		return Collections.emptyMap();
	}

	@Override
	default Optional<String> getValue() {
		return Optional.empty();
	}

	String render(String name);
}
