package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.util.MonadOption;

public interface Tokenizer {
    MonadOption<Token> evaluate();
}
