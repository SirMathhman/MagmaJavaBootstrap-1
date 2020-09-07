package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.node.Token;

import java.util.Optional;

public interface TokenizerFactory {
    Tokenizer create(String content);

    @Deprecated
    Optional<Token> parse(String content, Lexer lexer);
}
