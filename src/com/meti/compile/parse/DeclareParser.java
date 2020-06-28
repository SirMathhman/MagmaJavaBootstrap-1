package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.type.Type;
import com.meti.util.Scopes;

public class DeclareParser implements Parser {
	private final Scopes scopes;

	public DeclareParser(Scopes scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean canParse(String content) {
		return content.trim().contains(" ");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String trim = content.trim();
		int space = trim.lastIndexOf(' ');
		String typeString = trim.substring(0, space).trim();
		String name = trim.substring(space + 1).trim();
		Type type = compiler.resolveName(typeString);
		scopes.define(name, type);
		return new DeclareNode(name, type);
	}

	private static final class DeclareNode implements Node {
		private final String name;
		private final Type type;

		private DeclareNode(String name, Type type) {
			this.name = name;
			this.type = type;
		}

		@Override
		public String render() {
			return type.render(name) + ";";
		}
	}
}
