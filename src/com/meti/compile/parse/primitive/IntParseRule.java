package com.meti.compile.parse.primitive;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.primitive.IntNode;
import com.meti.compile.parse.ParseRule;

import java.util.Optional;

public class IntParseRule implements ParseRule {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		try {
			int value = Integer.parseInt(content);
			return Optional.of(new IntNode(value));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
