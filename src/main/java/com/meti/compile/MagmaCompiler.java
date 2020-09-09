package com.meti.compile;

import com.meti.compile.lex.MagmaLexer;
import com.meti.compile.node.Token;
import com.meti.compile.process.FixProcessStage;
import com.meti.compile.process.ProcessStage;
import com.meti.compile.process.TypeProcessStage;

import static com.meti.util.Monad.Monad;

public class MagmaCompiler implements Compiler {
    private final ProcessStage typeStage = new TypeProcessStage();
    private final ProcessStage fixStage = new FixProcessStage();
    private final MagmaLexer magmaLexer = new MagmaLexer();

    @Override
    public String compile(String value) {
        return "{%s}".formatted(value)
                .transform(magmaLexer::parseToOption)
                .applyOrThrow(this::applyPipeline, createInvalidTree(value));
    }

    private IllegalArgumentException createInvalidTree(String value) {
        return "Failed to tokenize root value of: %s"
                .formatted(value)
                .transform(IllegalArgumentException::new);
    }

    private String applyPipeline(Token token) {
        return Monad(token)
                .map(fixStage::process)
                .map(typeStage::process)
                .map(Token::render)
                .extract(String::length)
                .apply(this::unwrap);
    }

    private String unwrap(String renderedToken, int length) {
        return renderedToken.substring(1, length - 1);
    }
}