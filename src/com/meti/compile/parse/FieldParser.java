package com.meti.compile.parse;

import com.meti.MagmaException;
import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.type.StructType;
import com.meti.compile.type.Type;
import com.meti.util.Structures;

public class FieldParser implements Parser {
	private final Structures structures;

	public FieldParser(Structures structures) {
		this.structures = structures;
	}

	@Override
	public boolean canParse(String content) {
		return content.contains(".");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int period = content.indexOf('.');
		String before = content.substring(0, period).trim();
		String after = content.substring(period + 1).trim();
		Type type = compiler.resolveValue(before);
		if (type instanceof StructType) {
			String name = ((StructType) type).getName();
			if (structures.isDefined(name)) {
				if (structures.hasField(name, after)) {
					Node node = compiler.parse(before);
					return new FieldNode(node, after);
				} else {
					throw new MagmaException("Invalid field " + after + " of " + name);
				}
			} else {
				throw new MagmaException(before + " is not a defined structure.");
			}
		} else {
			throw new MagmaException(before + " is not a structure.");
		}
	}

	private static final class FieldNode implements Node {
		private final Node inner;
		private final String value;

		private FieldNode(Node inner, String value) {
			this.inner = inner;
			this.value = value;
		}

		@Override
		public String render() {
			return inner.render() + "." + value;
		}
	}
}
