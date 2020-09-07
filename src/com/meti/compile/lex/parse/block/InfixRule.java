package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Token;
import com.meti.compile.node.block.InfixToken;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfixRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        return streamValues(content).count() == 3;
    }

    @Override
    public Token parseQualified(String content, Lexer lexer) {
        List<Token> items = streamValues(content)
                .map(lexer::parse)
                .collect(Collectors.toList());
        Token value0 = items.get(0);
        Token operator = items.get(1);
        Token value1 = items.get(2);
        return new InfixToken(operator, value0, value1);
    }

    private Stream<String> streamValues(String content) {
        String[] values = content.split(" ");
        List<String> list = List.of(values);
        return list.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim);
    }
}
