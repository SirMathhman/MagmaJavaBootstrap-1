package com.meti.compile.unit;

import com.meti.MagmaException;
import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.StructNode;
import com.meti.compile.type.StructType;
import com.meti.compile.type.Type;
import com.meti.util.Structures;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StructUnit implements Unit {
	private final Structures structures;

	public StructUnit(Structures structures) {
		this.structures = structures;
	}

	@Override
	public boolean canParse(String content) {
		return content.startsWith("struct ") && content.contains("{") && content.contains("}");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int start = content.indexOf('{');
		int end = content.indexOf('}');
		String name = content.substring(7, start).trim();
		Map<String, Type> fields = Arrays.stream(content.substring(start + 1, end).split(";"))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.collect(Collectors.toMap(StructUnit::parseFieldName, value -> parseFieldType(value, compiler)));
		structures.define(name, fields);
		return new StructNode(name, fields);
	}

	private static String parseFieldName(String fieldString) {
		int colon = fieldString.indexOf(':');
		return fieldString.substring(0, colon).trim();
	}

	private static Type parseFieldType(String fieldString, Compiler compiler) {
		int colon = fieldString.indexOf(':');
		String value = fieldString.substring(colon + 1).trim();
		return compiler.resolveName(value);
	}

	@Override
	public boolean canResolve(String name) {
		return name.startsWith("struct ") || structures.isDefined(name);
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		if (structures.isDefined(name)) {
			return new StructType(name);
		}
		String structName = name.substring(7).trim();
		if (structures.isDefined(structName)) {
			return new StructType(structName);
		} else {
			throw new MagmaException("Invalid struct: " + structName);
		}
	}

}
