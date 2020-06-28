package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

public class AssignParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.contains("=");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int index = content.indexOf('=');
		String destinationString = content.substring(0, index).trim();
		String sourceString = content.substring(index + 1).trim();
		Node destination = compiler.parse(destinationString);
		Node source = compiler.parse(sourceString);
		return new AssignNode(destination, source);
	}

	private static final class AssignNode implements Node {
		private final Node destination;
		private final Node source;

		private AssignNode(Node destination, Node source) {
			this.destination = destination;
			this.source = source;
		}

		@Override
		public String render() {
			return destination.render() + "=" + source.render() + ";";
		}
	}
}
