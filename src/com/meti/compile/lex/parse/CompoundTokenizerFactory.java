package com.meti.compile.lex.parse;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.Tokenizer;
import com.meti.compile.node.Token;
import com.meti.util.Monad;
import com.meti.util.MonadOption;
import com.meti.util.MonadStream;

import java.util.Collection;
import java.util.Optional;

public abstract class CompoundTokenizerFactory implements TokenizerFactory {
    @Override
    public Optional<Token> parse(String content, Lexer lexer) {
        return supplyFactories().stream()
                .map(rule -> rule.parse(content, lexer))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public abstract Collection<TokenizerFactory> supplyFactories();

    public abstract MonadStream<TokenizerFactory> streamFactories();

    @Override
    public Tokenizer create(String content) {
        return new Monad<>(streamFactories())
                .with(content).reverse()
                .apply(CompoundTokenizer::new);
    }

    private static class CompoundTokenizer implements Tokenizer {
        private final String content;
        private final MonadStream<TokenizerFactory> factories;

        public CompoundTokenizer(String content, MonadStream<TokenizerFactory> factories) {
            this.content = content;
            this.factories = factories;
        }

        @Override
        public MonadOption<Token> evaluate() {
            return factories.with(content)
                    .map(TokenizerFactory::create)
                    .map(Tokenizer::evaluate)
                    .flatMap(MonadOption::stream)
                    .findFirst();
        }
    }
}
