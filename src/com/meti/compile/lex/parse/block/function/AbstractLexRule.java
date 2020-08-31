package com.meti.compile.lex.parse.block.function;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.block.FunctionNode;
import com.meti.compile.node.block.FunctionNodeBuilder;

public class AbstractLexRule extends FunctionLexRule {
    @Override
    protected FunctionNode finalize(String content, Lexer lexer, FunctionNodeBuilder builder) {
        return builder.build();
    }

    @Override
    protected String extractReturnType(String content) {
        int paramEnd = content.indexOf(')');
        int returnStart = content.indexOf(':', paramEnd);
        String value = content.substring(returnStart + 1);
        return value.trim();
    }

    @Override
    protected boolean canQualifyMore(String content) {
        return true;
    }
}