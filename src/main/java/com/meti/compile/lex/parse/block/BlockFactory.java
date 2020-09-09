package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.ValueToken;
import com.meti.compile.node.block.BlockToken;
import com.meti.util.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.meti.util.FunctionalUtilities.IdentityPredicate;
import static com.meti.util.Some.Some;

public class BlockFactory implements TokenizerFactory {
    @Override
    public Tokenizer create(String content) {
        return new BlockTokenizer(content);
    }

    private static class BlockTokenizer implements Tokenizer {
        private final String content;

        public BlockTokenizer(String content) {
            this.content = content;
        }

        @Override
        public Option<Token> evaluate() {
            return Some(content)
                    .extract(value -> value.startsWith("{"))
                    .extractStart(value -> value.endsWith("}"))
                    .filterMiddle(IdentityPredicate)
                    .filterEnd(IdentityPredicate)
                    .onlyFirst()
                    .supply(this::extractChildren)
                    .map(this::parseChildren)
                    .map(BlockToken::new);
        }

        private List<Token> parseChildren(List<String> children) {
            return children.stream()
                    .filter(s -> !s.isBlank())
                    .map(String::trim)
                    .map(ValueToken::new)
                    .collect(Collectors.toList());
        }

        private List<String> extractChildren() {
            String value = content.substring(1, content.length() - 1);
            List<String> results = new ArrayList<>();
            StringBuilder cache = new StringBuilder();
            int length = value.length();
            int depth = 0;
            for (int i = 0; i < length; i++) {
                char c = value.charAt(i);
                if (';' == c && 0 == depth) {
                    results.add(cache.toString());
                    cache = new StringBuilder();
                } else if ('}' == c && 1 == depth) {
                    cache.append('}');
                    depth--;
                    results.add(cache.toString());
                    cache = new StringBuilder();
                } else {
                    if ('{' == c) depth++;
                    if ('}' == c) depth--;
                    cache.append(c);
                }
            }
            results.add(cache.toString());
            results.removeIf(String::isBlank);
            return results;
        }
    }
}
