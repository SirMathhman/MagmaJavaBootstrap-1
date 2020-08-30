package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.FunctionNodeBuilder;
import com.meti.compile.type.InlineTypePair;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionLexRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        //native def printf(format : String, value : Any) : Void;
        int paramStart = content.indexOf('(');
        if (paramStart == -1) return false;
        String header = content.substring(0, paramStart).trim();
        //native def printf
        int lastSpace = header.lastIndexOf(' ');
        if (lastSpace == -1) return false;
        String keyString = header.substring(0, lastSpace).trim();
        //native def
        List<String> flags = Arrays.stream(keyString.split(" "))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());
        return flags.contains("def");
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        int paramStart = content.indexOf('(');
        int paramEnd = content.indexOf(')');
        List<TypePair> parameters = Arrays.stream(content.substring(paramStart + 1, paramEnd).trim().split(","))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(paramString -> parseParameter(paramString, lexer)).
                        collect(Collectors.toList());
        String name = content.substring(4, paramStart).trim();
        int returnStart = content.indexOf(':', paramEnd);
        int valueSeparator = content.indexOf("=>");
        int returnEnd = valueSeparator == -1 ? content.length() : valueSeparator;
        String returnString = content.substring(returnStart + 1, returnEnd)
                .trim();
        Type returnType = lexer.resolve(returnString);
        FunctionNodeBuilder builder = new FunctionNodeBuilder()
                .withName(name)
                .withReturnType(returnType)
                .withParameters(parameters);
        if (valueSeparator != -1) {
            Node value = content.substring(valueSeparator + 2)
                    .trim()
                    .transform(lexer::parse);
            builder = builder.withValue(value);
        }
        return builder.build();
    }

    private TypePair parseParameter(String paramString, Lexer lexer) {
        int separator = paramString.indexOf(':');
        String name = paramString.substring(0, separator).trim();
        String typeString = paramString.substring(separator + 1).trim();
        Type type = lexer.resolve(typeString);
        return new InlineTypePair(name, type);
    }
}