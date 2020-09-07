package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.util.Option;

public interface Tokenizer {
    Option<Token> create();
}
