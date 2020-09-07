package com.meti.compile.lex.parse.scope;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.scope.VariableToken;

import java.util.Optional;

public class VariableTokenizerFactory implements TokenizerFactory {
	@Override
	public Optional<Token> parse(String content, Lexer lexer) {
		return Optional.of(new VariableToken(content));
	}

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
