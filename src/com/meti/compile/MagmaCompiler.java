package com.meti.compile;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.RootLexer;
import com.meti.compile.lex.parse.MagmaTokenizerFactory;
import com.meti.compile.lex.parse.TokenizerFactory;
import com.meti.compile.lex.resolve.MagmaResolveRule;
import com.meti.compile.lex.resolve.ResolveRule;
import com.meti.compile.node.Token;
import com.meti.compile.process.FixProcessStage;
import com.meti.compile.process.ProcessStage;
import com.meti.compile.process.TypeProcessStage;
import com.meti.util.Option;

public class MagmaCompiler implements Compiler {
    private final TokenizerFactory rootParserRule = new MagmaTokenizerFactory();
    private final ResolveRule rootResolveRule = new MagmaResolveRule();
    private final Lexer lexer = new RootLexer(rootParserRule, rootResolveRule);
    private final ProcessStage typeStage = new TypeProcessStage();
    private final ProcessStage fixStage = new FixProcessStage();

    @Override
    public String compileImpl(String value) {
        return "{%s}".formatted(value)
                .transform(lexer::parseToOption)
                .applyOrThrow(this::applyPipeline, createInvalidTree(value));
    }

    private IllegalArgumentException createInvalidTree(String value) {
        return "Failed to tokenize root value of: %s"
                .formatted(value)
                .transform(IllegalArgumentException::new);
    }

    private String applyPipeline(Token token) {
        Token withFixes = fixStage.process(token);
        Token withTypes = typeStage.process(withFixes);
        String result = withTypes.render();
        return result.substring(1, result.length() - 1);
    }
}