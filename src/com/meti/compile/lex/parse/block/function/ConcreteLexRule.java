package com.meti.compile.lex.parse.block.function;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Token;
import com.meti.compile.node.block.FunctionToken;
import com.meti.compile.node.block.FunctionNodeBuilder;

public class ConcreteLexRule extends FunctionLexRule {
    @Override
    protected FunctionToken finalize(String content, Lexer lexer, FunctionNodeBuilder builder) {
        int separator = content.indexOf("=>");
        Token value = content.substring(separator + 2)
                .trim().transform(lexer::parse);
        return builder.withValue(value).build();
    }

    @Override
    protected String extractReturnType(String content) {
        int paramEnd = content.indexOf(')');
        int returnStart = content.indexOf(':', paramEnd);
        int returnEnd = content.indexOf("=>");
        String value = content.substring(returnStart + 1, returnEnd);
        return value.trim();
    }

    @Override
    protected boolean canQualifyMore(String content) {
        return content.contains("=>");
    }
}