package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

public class QuantityParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("(") && content.endsWith(")");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String valueString = content.substring(1, content.length() - 1);
		Node value = compiler.parse(valueString);
		return new QuantityNode(value);
	}

	private static final class QuantityNode implements Node {
		private final Node value;

		private QuantityNode(Node value) {
			this.value = value;
		}

		@Override
		public String render() {
			return "(" + value.render() + ")";
		}
	}
}
