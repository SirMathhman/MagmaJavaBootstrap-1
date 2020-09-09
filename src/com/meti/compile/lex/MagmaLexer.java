package com.meti.compile.lex;

import com.meti.compile.lex.parse.MagmaTokenizerFactory;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.lex.resolve.MagmaResolveRule;
import com.meti.compile.lex.resolve.ResolveRule;
import com.meti.compile.node.Token;
import com.meti.compile.type.Type;
import com.meti.util.Option;

public class MagmaLexer implements Lexer {
    private final TokenizerFactory rootParserRule = new MagmaTokenizerFactory();
    private final ResolveRule rootResolveRule = new MagmaResolveRule();
    private final Lexer lexer = new RootLexer(rootParserRule, rootResolveRule);

    public Option<Token> parseToOption(String content) {
        return lexer.parseToOption(content);
    }

    @Override
    public Token parse(String content) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type resolve(String name) {
        throw new UnsupportedOperationException();
    }

    public MagmaLexer() {
    }
}