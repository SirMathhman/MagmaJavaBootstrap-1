package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

public class DereferenceParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("*");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String valueString = content.substring(1).trim();
		Node value = compiler.parse(valueString);
		return new DereferenceNode(value);
	}

	private static final class DereferenceNode implements Node {
		private final Node node;

		private DereferenceNode(Node node) {
			this.node = node;
		}

		@Override
		public String render() {
			return "*" + node.render();
		}
	}
}
