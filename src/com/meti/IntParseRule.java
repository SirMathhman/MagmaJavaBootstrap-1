package com.meti;

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
