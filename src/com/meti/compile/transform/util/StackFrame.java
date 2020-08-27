package com.meti.compile.transform.util;

import com.meti.compile.type.Type;

import java.util.*;
import java.util.function.Function;

public class StackFrame {
	private final Map<String, Declaration> definitions = new HashMap<>();
	private final List<String> generics = new ArrayList<>();

	public String define(String name, Type type) {
		if (!definitions.containsKey(name)) {
			definitions.put(name, new Declaration(name));
		}
		return definitions.get(name).define(type);
	}

	public boolean isDefined(String name) {
		return definitions.containsKey(name);
	}

	public <T> Optional<T> lookup(String name, Function<Type, T> function) {
		return Optional.of(name)
				.filter(definitions::containsKey)
				.map(definitions::get)
				.map(definition -> definition.applyFirst(function));
	}

	public Optional<String> lookup(String name, Type type) {
		return Optional.of(name)
				.filter(definitions::containsKey)
				.map(definitions::get)
				.map(definition -> definition.lookup(type));
	}
}
