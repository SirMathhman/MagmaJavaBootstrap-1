package com.meti.compile.lex;

import com.meti.compile.node.Token;

import java.util.Optional;

public interface Tokenizer {
    Optional<Token> create();
}
