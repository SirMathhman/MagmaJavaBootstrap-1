package com.meti.block;

import com.meti.Compiler;
import com.meti.FilteredParseRule;
import com.meti.Node;

import java.util.List;
import java.util.stream.Collectors;

public class BlockRule implements FilteredParseRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("{") &&
		       content.endsWith("}");
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		List<String> childContent = extractChildren(content);
		List<Node> collect = childContent
				.stream()
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(compiler::parse)
				.collect(Collectors.toList());
		return new BlockNode(collect);
	}

	public static List<String> extractChildren(String content) {
		String value = content.substring(1, content.length() - 1);
		String[] valueArray = value.split(";");
		return List.of(valueArray);
	}
}
