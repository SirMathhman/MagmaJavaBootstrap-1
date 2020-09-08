package com.meti.compile.lex.parse;

import com.meti.compile.lex.Tokenizer;

public interface TokenizerFactory {
    Tokenizer create(String content);
}
