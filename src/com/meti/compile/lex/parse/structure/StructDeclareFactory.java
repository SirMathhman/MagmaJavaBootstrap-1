package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.ValueToken;
import com.meti.compile.node.structure.StructDeclareToken.Builder;
import com.meti.util.*;

import java.util.List;

public class StructDeclareFactory implements TokenizerFactory {
    @Override
    public Tokenizer create(String content) {
        return new TokenizerImpl(content);
    }

    private static class TokenizerImpl implements Tokenizer {
        private final String content;

        public TokenizerImpl(String content) {
            this.content = content;
        }

        private Token parseFields(Builder builder) {
            return extractChildren()
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .map(ValueToken::new)
                    .reduce(builder, Builder::withChild)
                    .build();
        }

        private MonadStream<String> extractChildren() {
            return Monad.Monad(content)
                    .extract(value -> value.indexOf('{'))
                    .extractStart(value -> value.indexOf('}'))
                    .map((value, start, end) -> value.substring(start + 1, end))
                    .map(String::trim)
                    .map(value -> value.split(","))
                    .map(List::of)
                    .apply(MonadStream::Stream);
        }

        private Builder createBuilder() {
            return Monad.Monad(content)
                    .extract(value -> value.indexOf('<'))
                    .extractStart(value -> value.indexOf('>'))
                    .map((value, start, end) -> value.substring(start + 1, end))
                    .map(String::trim)
                    .with(new Builder())
                    .reverse()
                    .apply(Builder::withName);
        }

        @Override
        public Option<Token> evaluate() {
            return Some.Some(content)
                    .filter(value -> value.startsWith("<"))
                    .filter(value -> value.contains(">"))
                    .filter(value -> value.contains("{"))
                    .filter(value -> value.endsWith("}"))
                    .replace(this::createBuilder)
                    .map(this::parseFields);
        }
    }
}
