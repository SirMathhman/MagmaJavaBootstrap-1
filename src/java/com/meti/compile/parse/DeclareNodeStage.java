package com.meti.compile.parse;

import com.meti.compile.node.DeclareNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.StringNode;
import com.meti.compile.type.PrimitiveType;
import com.meti.compile.type.StringType;
import com.meti.compile.type.Type;

public class DeclareNodeStage implements NodeStage {
	@Override
	public boolean canAccept(Node node) {
		return node.getValue()
				.filter(s -> s.contains(":") || s.contains("="))
				.isPresent();
	}

	@Override
	public Node accept(Node node) {
		String value = node.getValue().orElseThrow();
		int colon = value.indexOf(':');
		int equals = value.indexOf('=');
		String name = parseName(value, colon, equals);
		Type type = parseType(value, colon, equals);
		Node initial = parseInitial(value, equals);
		return new DeclareNode(name, type, initial);
	}

	private static String parseName(String value, int colon, int equals) {
		if (-1 == colon) {
			return value.substring(0, equals).trim();
		} else {
			return value.substring(0, colon).trim();
		}
	}

	private static Type parseType(String value, int colon, int equals) {
		if (-1 == colon) {
			return PrimitiveType.UNKNOWN;
		} else if (-1 == equals) {
			return new StringType(value.substring(colon + 1).trim());
		} else {
			return new StringType(value.substring(colon + 1, equals).trim());
		}
	}

	private static Node parseInitial(String value, int equals) {
		if (-1 == equals) {
			return null;
		} else {
			return new StringNode(value.substring(equals + 1).trim());
		}
	}
}
