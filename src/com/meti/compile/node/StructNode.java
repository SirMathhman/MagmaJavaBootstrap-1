package com.meti.compile.node;

import com.meti.compile.type.Type;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class StructNode implements Node {
	private final String name;
	private final Map<String, Type> types;

	public StructNode(String name, Map<String, Type> types) {
		this.name = name;
		this.types = Collections.unmodifiableMap(types);
	}

	@Override
	public String render() {
		String content = renderContent();
		return "struct " + name + content;
	}

	private String renderContent() {
		return types.keySet()
				.stream()
				.map(this::renderField)
				.map(value -> value + ";")
				.collect(Collectors.joining("", "{", "}"));
	}

	private String renderField(String name) {
		return types.get(name).render(name);
	}
}
