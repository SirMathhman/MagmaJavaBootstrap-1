package com.meti.compile.process.util;

import com.meti.compile.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Declaration {
	private final Map<Type, String> aliases = new HashMap<>();
	private final String value;
	private int counter = -2;

	public Declaration(String value) {
		this.value = value;
	}

	public <T> T applyFirst(Function<Type, T> function) {
		return aliases.keySet()
				.stream()
				.findFirst()
				.map(function)
				.orElseThrow();
	}

	public String define(Type type) {
		if (aliases.containsKey(type)) {
			throw new IllegalArgumentException("%s has already been defined with type %s".formatted(value, type));
		}
		String alias = next();
		aliases.put(type, alias);
		return alias;
	}

	public List<Type> listTypes() {
		return new ArrayList<>(aliases.keySet());
	}

	private String next() {
		counter++;
		if (-1 == counter) return value;
		else return "%s%d_".formatted(value, counter);
	}

	public String lookup(Type type) {
		if (aliases.containsKey(type)) {
			return aliases.get(type);
		} else {
			String message = ("An alias of type \"%s\" " +
			                  "doesn't exist in the registered aliases of %s").formatted(type, aliases);
			throw new IllegalArgumentException(message);
		}
	}
}
