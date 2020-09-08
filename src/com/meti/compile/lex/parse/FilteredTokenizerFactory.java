package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Token;

@Deprecated
public abstract class FilteredTokenizerFactory implements TokenizerFactory {

    public abstract boolean canQualify(String content);

    public abstract Token parseQualified(String content, Lexer lexer);
}
