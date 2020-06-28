package com.meti.compile.unit;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.type.InlineType;
import com.meti.compile.type.PointerType;
import com.meti.compile.type.Type;

public class StringUnit implements Unit {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("\"") && content.endsWith("\"");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String value = content.substring(1, content.length() - 1);
		return new StringNode(value);
	}

	@Override
	public boolean canResolve(String name) {
		return "string".equals(name);
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		return new PointerType(new InlineType("char"));
	}

	private static final class StringNode implements Node {
		private final String value;

		private StringNode(String value) {
			this.value = value;
		}

		@Override
		public String render() {
			return "\"" + value + "\"";
		}
	}
}
