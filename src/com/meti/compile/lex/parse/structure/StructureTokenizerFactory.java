package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.FilteredTokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.structure.StructureNodeBuilder;
import com.meti.compile.type.FieldBuilder;
import com.meti.compile.type.Field;
import com.meti.compile.type.Type;
import com.meti.util.Monad;

import java.util.List;
import java.util.stream.Collectors;

public class StructureTokenizerFactory extends FilteredTokenizerFactory {
    private static final String HEADER = "struct ";

    @Override
    public boolean canQualify(String content) {
        return content.startsWith(HEADER);
    }

    @Override
    public Token parseQualified(String content, Lexer lexer) {
        return new StructureNodeBuilder()
                .withName(() -> parseName(content))
                .withFields(() -> parseFields(content, lexer))
                .build();
    }

    private List<Field> parseFields(String content, Lexer lexer) {
        return extractFields(content)
                .append(lexer)
                .map(this::parseFields)
                .complete();
    }

    private List<Field> parseFields(List<String> list, Lexer lexer) {
        return list.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(s -> parseField(s, lexer))
                .collect(Collectors.toList());
    }

    private Monad<List<String>> extractFields(String content) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf('{'))
                .extractStart(String::length)
                .map((value, start, end) -> value.substring(start + 1, end - 2))
                .map(value -> value.split(";"))
                .map(List::of);
    }

    private String parseName(String content) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf('{'))
                .map((value, index) -> value.substring(0, index))
                .complete().trim().transform(value -> value.substring(HEADER.length()));
    }

    private Field parseField(String fieldString, Lexer lexer) {
        return FieldBuilder.create()
                .withName(() -> parseFieldName(fieldString))
                .withType(() -> parseFieldType(fieldString, lexer))
                .build();
    }

    private String parseFieldName(String fieldString) {
        return Monad.Monad(fieldString)
                .extract(value -> value.indexOf(':'))
                .map((value, index) -> value.substring(0, index))
                .complete().trim();
    }

    private Type parseFieldType(String fieldString, Lexer lexer) {
        return Monad.Monad(fieldString)
                .extract(value -> value.indexOf(":"))
                .map((value, index) -> value.substring(index + 1))
                .complete().trim().transform(lexer::resolve);
    }

    @Override
    public Tokenizer create(String content) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}