package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Token;
import com.meti.compile.node.structure.FieldToken;
import com.meti.util.Pair;
import com.meti.util.Unit;

public class FieldLexRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        return content.contains("->");
    }

    @Override
    public Token parseQualified(String content, Lexer lexer) {
        return new Pair<>(content, lexer)
                .zipSecond(this::parseParent)
                .mapFirst(this::parseName)
                .swap().implode(FieldToken::new)
                .complete();
    }

    private String parseName(String content) {
        return new Unit<>(content)
                .explode(value -> value.indexOf("->"))
                .implode((value, index) -> value.substring(index + 2))
                .map(String::trim)
                .complete();
    }

    private Token parseParent(String content, Lexer lexer) {
        return new Unit<>(content)
                .explode(value -> value.indexOf("->"))
                .implode((value, index) -> value.substring(0, index))
                .map(String::trim)
                .map(lexer::parse)
                .complete();
    }
}
