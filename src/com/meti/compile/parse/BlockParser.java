package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.util.DepthSplitter;
import com.meti.util.Scopes;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockParser implements Parser {
	private final Scopes scopes;

	public BlockParser(Scopes scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean canParse(String content) {
		return content.startsWith("{") && content.endsWith("}");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String childrenString = content.substring(1, content.length() - 1);
		Collection<String> items = DepthSplitter.BRACKET.split(childrenString);
		scopes.enter();
		List<Node> children = parseChildren(compiler, items);
		scopes.exit();
		return new BlockNode(children);
	}

	private static List<Node> parseChildren(Compiler compiler, Collection<String> items) {
		return items.stream()
				.map(String::trim)
				.map(compiler::parse)
				.collect(Collectors.toList());
	}

	private static final class BlockNode implements Node {
		private final Collection<? extends Node> children;

		private BlockNode(Collection<? extends Node> children) {
			this.children = children;
		}

		@Override
		public String render() {
			return children.stream()
					.map(Node::render)
					.collect(Collectors.joining("", "{", "}"));
		}
	}
}