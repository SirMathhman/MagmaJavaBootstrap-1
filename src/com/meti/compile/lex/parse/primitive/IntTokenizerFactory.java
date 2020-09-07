package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.primitive.IntToken;

import java.util.Optional;

public class IntTokenizerFactory implements TokenizerFactory {
	@Override
	public Optional<Token> parse(String content, Lexer lexer) {
		try {
			int value = Integer.parseInt(content);
			return Optional.of(new IntToken(value));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
