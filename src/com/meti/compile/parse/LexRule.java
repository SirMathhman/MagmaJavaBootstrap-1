package com.meti.compile.parse;

import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

import java.util.Optional;

public interface LexRule {
	Optional<Node> parse(String content, Lexer lexer);
}
