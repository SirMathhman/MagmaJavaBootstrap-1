package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public class CastParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("(") && content.contains(")");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int end = content.indexOf(')');
		String typeString = content.substring(1, end).trim();
		Type type = compiler.resolveName(typeString);
		String value = content.substring(end + 1).trim();
		Node valueNode = compiler.parse(value);
		return new CastNode(valueNode, type);
	}

	private static final class CastNode implements Node {
		private final Type type;
		private final Node value;

		private CastNode(Node value, Type type) {
			this.value = value;
			this.type = type;
		}

		@Override
		public String render() {
			return "(" + type.render() + ")" + value.render();
		}
	}
}
