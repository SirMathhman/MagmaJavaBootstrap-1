package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Tokenizer;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.primitive.CharToken;
import com.meti.util.MonadOption;

import static com.meti.util.Some.Some;

public class CharTokenizerFactory implements TokenizerFactory {
    @Override
    public Tokenizer create(String content) {
        return new CharTokenizer(content);
    }

    private static class CharTokenizer implements Tokenizer {
        private final String content;

        public CharTokenizer(String content) {
            this.content = content;
        }

        @Override
        public MonadOption<Token> evaluate() {
            return Some(content)
                    .filter(value -> value.startsWith("'"))
                    .filter(value -> value.endsWith("'"))
                    .filter(value -> value.length() == 3)
                    .map(value -> value.charAt(1))
                    .map(CharToken::new);
        }
    }
}
