package com.meti.compile.lex.parse.block.function;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.ValueToken;
import com.meti.compile.node.block.FunctionNodeBuilder;
import com.meti.compile.node.block.FunctionToken;
import com.meti.util.Monad;

public class ConcreteTokenizerFactory extends FunctionTokenizerFactory {
    @Override
    protected FunctionToken finalize(String content, Lexer lexer, FunctionNodeBuilder builder) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf("=>"))
                .map((value, index) -> value.substring(index + 2))
                .map(String::trim)
                .map(ValueToken::new)
                .map(builder::withValue)
                .apply(FunctionNodeBuilder::build);
    }

    @Override
    protected String extractReturnType(String content) {
        return Monad.Monad(content)
                .extract(value -> value.indexOf(')'))
                .replaceEnd((value, index) -> value.indexOf(':', index))
                .extractStart(value -> value.indexOf("=>"))
                .implode((value, start, end) -> value.substring(start + 1, end))
                .apply(String::trim);
    }

    @Override
    protected boolean canQualifyMore(String content) {
        return content.contains("=>");
    }
}