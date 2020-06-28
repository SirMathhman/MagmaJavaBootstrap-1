package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InvocationParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.contains("(") && content.endsWith(")");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int start = content.indexOf('(');
		String callerString = content.substring(0, start);
		Node caller = compiler.parse(callerString);
		List<Node> list = Arrays.stream(content.substring(start + 1, content.length() - 1).split(","))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(compiler::parse)
				.collect(Collectors.toList());
		return new InvocationNode(caller, list);
	}

	private static final class InvocationNode implements Node {
		private final Collection<Node> arguments;
		private final Node caller;

		private InvocationNode(Node caller, Collection<Node> arguments) {
			this.caller = caller;
			this.arguments = arguments;
		}

		@Override
		public String render() {
			String argString = renderArguments();
			return caller.render() + argString;
		}

		public String renderArguments() {
			return arguments.stream()
					.map(Node::render)
					.collect(Collectors.joining(",", "(", ")"));
		}
	}
}
