package com.meti.compile.lex.parse.scope;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.LexRule;
import com.meti.compile.node.Token;
import com.meti.compile.node.scope.VariableToken;

import java.util.Optional;

public class VariableLexRule implements LexRule {
	@Override
	public Optional<Token> parse(String content, Lexer lexer) {
		return Optional.of(new VariableToken(content));
	}
}
