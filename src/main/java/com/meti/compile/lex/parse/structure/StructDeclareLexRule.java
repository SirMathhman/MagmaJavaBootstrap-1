package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.structure.StructDeclareNode.Builder;
import com.meti.util.Unit;

import java.util.List;

public class StructDeclareLexRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        return content.startsWith("<") && content.contains(">") &&
                content.contains("{") && content.endsWith("}");
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        return createBuilderWithName(content)
                .with(content).supply(lexer)
                .implode(this::parseFields)
                .complete();
    }

    private Node parseFields(Builder builder, String content, Lexer lexer) {
        return extractChildren(content).stream()
                .filter(s -> !s.isBlank())
                .map(String::trim).map(lexer::parse)
                .reduce(builder, Builder::withChild, (oldBuilder, newBuilder) -> newBuilder)
                .build();
    }

    private List<String> extractChildren(String content) {
        return new Unit<>(content)
                .explode(value -> value.indexOf('{'))
                .explodeFirst(value -> value.indexOf('}'))
                .implode((value, start, end) -> value.substring(start + 1, end))
                .map(String::trim)
                .map(value -> value.split(","))
                .map(List::of)
                .complete();
    }

    private Unit<Builder> createBuilderWithName(String content) {
        return new Unit<>(content)
                .explode(value -> value.indexOf('<'))
                .explodeFirst(value -> value.indexOf('>'))
                .implode((value, start, end) -> value.substring(start + 1, end))
                .map(String::trim)
                .map(this::createBuilder);
    }

    private Builder createBuilder(String value) {
        return new Builder().withName(value);
    }
}
