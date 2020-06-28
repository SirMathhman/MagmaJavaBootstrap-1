package com.meti.util;

import com.meti.MagmaException;
import com.meti.compile.type.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Structures {
	private final Map<String, Map<String, Type>> structures = new HashMap<>();


	public void define(String name, Map<String, Type> fields) {
		if (structures.containsKey(name)) {
			throw new MagmaException(name + " has already been defined as a struct.");
		} else {
			structures.put(name, fields);
		}
	}

	public boolean hasField(String structName, String name) {
		return getField(structName, name).isPresent();
	}

	public Optional<Type> getField(String structName, String name) {
		Map<String, Type> map = structures.get(structName);
		if (map == null) {
			throw new IllegalArgumentException(structName + " is not defined.");
		} else {
			return Optional.ofNullable(map.get(name));
		}
	}

	public boolean isDefined(String structName) {
		return structures.containsKey(structName);
	}
}
