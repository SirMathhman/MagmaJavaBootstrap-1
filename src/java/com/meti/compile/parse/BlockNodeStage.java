package com.meti.compile.parse;

import com.meti.compile.node.BlockNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.StringNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockNodeStage implements NodeStage {
	@Override
	public boolean canAccept(Node node) {
		return node.getValue()
				.filter(s -> s.startsWith("{") && s.endsWith("}"))
				.isPresent();
	}

	@Override
	public Node accept(Node node) {
		String value = node.getValue().orElseThrow();
		String content = value.substring(1, value.length() - 1);
		Collection<String> items = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (';' == c && 0 == depth) {
				items.add(builder.toString());
				builder = new StringBuilder();
			} else if ('}' == c && 1 == depth) {
				builder.append("}");
				items.add(builder.toString());
				builder = new StringBuilder();
			} else {
				if ('{' == c) depth++;
				if ('}' == c) depth--;
				builder.append(c);
			}
		}
		items.add(builder.toString());
		List<StringNode> children = items.stream()
				.filter(item -> !item.isBlank())
				.map(String::trim)
				.map(StringNode::new)
				.collect(Collectors.toList());
		return new BlockNode(children);
	}
}
