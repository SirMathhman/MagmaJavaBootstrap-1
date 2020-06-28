package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.util.Scopes;

public class VariableParser implements Parser {
	private final Scopes scopes;

	public VariableParser(Scopes scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean canParse(String content) {
		return scopes.isDefined(content);
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		return new VariableNode(content);
	}

	private static final class VariableNode implements Node {
		private final String content;

		private VariableNode(String content) {
			this.content = content;
		}

		@Override
		public String render() {
			return content;
		}
	}
}
