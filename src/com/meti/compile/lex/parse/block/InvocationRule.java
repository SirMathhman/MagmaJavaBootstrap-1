package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.InvocationNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InvocationRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.contains("(") && content.endsWith(")");
	}

	@Override
	public Node parseQualified(String content, Lexer lexer) {
		//printf(3, 4)
		int separator = content.indexOf('(');
		String value = content.substring(0, separator).trim();
		//printf
		Node caller = lexer.parse(value);
		String trim = content.substring(separator + 1, content.length() - 1).trim();
		List<Node> arguments = Arrays.stream(trim.split(","))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(lexer::parse)
				.collect(Collectors.toList());
		return new InvocationNode(caller, arguments);
	}
}
