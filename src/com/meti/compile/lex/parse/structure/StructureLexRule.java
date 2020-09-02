package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.structure.StructureNodeBuilder;
import com.meti.compile.type.FieldBuilder;
import com.meti.compile.type.Field;
import com.meti.compile.type.Type;
import com.meti.util.Unit;

import java.util.List;
import java.util.stream.Collectors;

public class StructureLexRule extends FilteredLexRule {
    private static final String HEADER = "struct ";

    @Override
    public boolean canQualify(String content) {
        return content.startsWith(HEADER);
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        return new StructureNodeBuilder()
                .withName(() -> parseName(content))
                .withFields(() -> parseFields(content, lexer))
                .build();
    }

    private List<Field> parseFields(String content, Lexer lexer) {
        return extractFields(content)
                .with(lexer)
                .implode(this::parseFields)
                .complete();
    }

    private List<Field> parseFields(List<String> list, Lexer lexer) {
        return list.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(s -> parseField(s, lexer))
                .collect(Collectors.toList());
    }

    private Unit<List<String>> extractFields(String content) {
        return new Unit<>(content)
                .explode(value -> value.indexOf('{'))
                .explodeFirst(String::length)
                .implode((value, start, end) -> value.substring(start + 1, end - 2))
                .map(value -> value.split(";"))
                .map(List::of);
    }

    private String parseName(String content) {
        return new Unit<>(content)
                .explode(value -> value.indexOf('{'))
                .implode((value, index) -> value.substring(0, index))
                .complete().trim().transform(value -> value.substring(HEADER.length()));
    }

    private Field parseField(String fieldString, Lexer lexer) {
        return new FieldBuilder()
                .withName(() -> parseFieldName(fieldString))
                .withType(() -> parseFieldType(fieldString, lexer))
                .build();
    }

    private String parseFieldName(String fieldString) {
        return new Unit<>(fieldString)
                .explode(value -> value.indexOf(':'))
                .implode((value, index) -> value.substring(0, index))
                .complete().trim();
    }

    private Type parseFieldType(String fieldString, Lexer lexer) {
        return new Unit<>(fieldString)
                .explode(value -> value.indexOf(":"))
                .implode((value, index) -> value.substring(index + 1))
                .complete().trim().transform(lexer::resolve);
    }
}