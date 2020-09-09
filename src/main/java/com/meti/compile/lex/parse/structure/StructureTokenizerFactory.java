package com.meti.compile.lex.parse.structure;

import com.meti.compile.instance.Field;
import com.meti.compile.instance.FieldBuilder;
import com.meti.compile.instance.Type;
import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.structure.StructureToken;
import com.meti.util.Monad;
import com.meti.util.Option;

import java.util.List;
import java.util.stream.Collectors;

import static com.meti.util.Monad.Monad;
import static com.meti.util.Some.Some;

public class StructureTokenizerFactory implements TokenizerFactory {
    private static final String HEADER = "struct ";

    @Override
    public Tokenizer create(String content) {
        return new StructureTokenizer(content);
    }

    private static class StructureTokenizer implements Tokenizer {
        private final String content;

        public StructureTokenizer(String content) {
            this.content = content;
        }

        private List<Field> parseFields(String content, Lexer lexer) {
            return extractFields(content)
                    .append(lexer)
                    .apply(this::parseFields);
        }

        private List<Field> parseFields(List<String> list, Lexer lexer) {
            return list.stream()
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .map(s -> parseField(s, lexer))
                    .collect(Collectors.toList());
        }

        private Monad<List<String>> extractFields(String content) {
            return Monad(content)
                    .extract(value -> value.indexOf('{'))
                    .extractStart(String::length)
                    .map((value, start, end) -> value.substring(start + 1, end - 2))
                    .map(value -> value.split(";"))
                    .map(List::of);
        }

        private String parseName(String content) {
            return Monad(content)
                    .extract(value -> value.indexOf('{'))
                    .map((value, index) -> value.substring(0, index))
                    .map(String::trim)
                    .apply(value -> value.substring(HEADER.length()));
        }

        private Field parseField(String fieldString, Lexer lexer) {
            return FieldBuilder.create()
                    .withName(() -> parseFieldName(fieldString))
                    .withType(() -> parseFieldType(fieldString, lexer))
                    .build();
        }

        private String parseFieldName(String fieldString) {
            return Monad(fieldString)
                    .extract(value -> value.indexOf(':'))
                    .map((value, index) -> value.substring(0, index))
                    .apply(String::trim);
        }

        private Type parseFieldType(String fieldString, Lexer lexer) {
            return Monad(fieldString)
                    .extract(value -> value.indexOf(":"))
                    .map((value, index) -> value.substring(index + 1))
                    .map(String::trim)
                    .apply(lexer::resolve);
        }


        @Override
        public Option<Token> evaluate() {
            return Some(content)
                    .with(HEADER)
                    .filterBoth(String::startsWith)
                    .ignoreLast()
                    .extract(s -> parseFields(s, null))
                    .mapStart(this::parseName)
                    .map(StructureToken::new);
        }
    }
}