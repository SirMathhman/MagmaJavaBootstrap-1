package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.BlockNode;

import java.util.List;
import java.util.stream.Collectors;

public class BlockRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("{") &&
		       content.endsWith("}");
	}

	@Override
	public Node parseQualified(String content, Lexer lexer) {
		List<String> childContent = extractChildren(content);
		List<Node> collect = childContent
				.stream()
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(lexer::parse)
				.collect(Collectors.toList());
		return new BlockNode(collect);
	}

	public static List<String> extractChildren(String content) {
		String value = content.substring(1, content.length() - 1);
		String[] valueArray = value.split(";");
		return List.of(valueArray);
	}
}
