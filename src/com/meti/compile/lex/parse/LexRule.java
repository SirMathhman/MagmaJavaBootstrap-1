package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Token;

import java.util.Optional;

public interface LexRule {
	Optional<Token> parse(String content, Lexer lexer);
}
