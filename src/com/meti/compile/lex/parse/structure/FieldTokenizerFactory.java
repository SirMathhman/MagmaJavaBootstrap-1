package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.FilteredTokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.structure.FieldToken;
import com.meti.util.Duad;
import com.meti.util.Monad;

public class FieldTokenizerFactory extends FilteredTokenizerFactory {
    @Override
    public boolean canQualify(String content) {
        return content.contains("->");
    }

    @Override
    public Token parseQualified(String content, Lexer lexer) {
        return new Duad<>(content, lexer)
                .zipSecond(this::parseParent)
                .mapStart(this::parseName)
                .reverse().map(FieldToken::new)
                .complete();
    }

    private String parseName(String content) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf("->"))
                .map((value, index) -> value.substring(index + 2))
                .map(String::trim)
                .complete();
    }

    private Token parseParent(String content, Lexer lexer) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf("->"))
                .map((value, index) -> value.substring(0, index))
                .map(String::trim)
                .map(lexer::parse)
                .complete();
    }

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
