package com.meti.compile.util;

import com.meti.compile.type.Type;

import java.util.HashMap;
import java.util.Map;

public class Declaration {
	private final Map<Type, String> aliases = new HashMap<>();
	private final String value;
	private int counter = -2;

	public Declaration(String value) {
		this.value = value;
	}

	public String define(Type type) {
		if (aliases.containsKey(type)) {
			throw new IllegalArgumentException("%s has already been defined with type %s".formatted(value, type));
		}
		String alias = next();
		aliases.put(type, alias);
		return alias;
	}

	private String next() {
		counter++;
		if (-1 == counter) return value;
		else return "%s%d_".formatted(value, counter);
	}
}
