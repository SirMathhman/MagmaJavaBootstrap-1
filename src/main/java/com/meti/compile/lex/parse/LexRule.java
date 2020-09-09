package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;

import java.util.Optional;

public interface LexRule {
	Optional<Node> parse(String content, Lexer lexer);
}
