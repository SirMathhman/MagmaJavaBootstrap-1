package com.meti.compile.util;

import com.meti.compile.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
