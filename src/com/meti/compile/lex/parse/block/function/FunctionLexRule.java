package com.meti.compile.lex.parse.block.function;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.FunctionNode;
import com.meti.compile.node.block.FunctionNodeBuilder;
import com.meti.compile.type.InlineField;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FunctionLexRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        int paramStart = content.indexOf('(');
        return paramStart != -1 && validateHeader(content) && canQualifyMore(content);
    }

    protected abstract boolean canQualifyMore(String content);

    private boolean validateHeader(String content) {
        String header = extractHeader(content);
        int lastSpace = header.lastIndexOf(' ');
        return lastSpace != -1 && hasKeyword(header);
    }

    private String extractHeader(String content) {
        int separator = content.indexOf('(');
        String value = content.substring(0, separator);
        return value.trim();
    }

    private boolean hasKeyword(String header) {
        int lastSpace = header.lastIndexOf(' ');
        String value = header.substring(0, lastSpace);
        String formatted = value.trim();
        List<String> flags = parseFlags(formatted);
        return flags.contains("def");
    }

    private List<String> parseFlags(String keyString) {
        return Arrays.stream(keyString.split(" "))
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        List<String> paramStrings = extractParamStrings(content);
        List<Field> parameters = parseParameters(lexer, paramStrings);

        String header = extractHeader(content);
        int lastSpace = header.lastIndexOf(' ');
        String name = header.substring(lastSpace + 1, content.indexOf('(')).trim();

        String formatted = extractReturnType(content);
        Type returnType = lexer.resolve(formatted);
        FunctionNodeBuilder builder = new FunctionNodeBuilder()
                .withName(name)
                .withReturnType(returnType)
                .withParameters(parameters);
        return finalize(content, lexer, builder);
    }

    protected abstract FunctionNode finalize(String content, Lexer lexer, FunctionNodeBuilder builder);

    protected abstract String extractReturnType(String content);

    private List<Field> parseParameters(Lexer lexer, List<String> paramStrings) {
        return paramStrings.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(paramString -> parseParameter(paramString, lexer))
                .collect(Collectors.toList());
    }

    private List<String> extractParamStrings(String content) {
        int start = content.indexOf('(');
        int end = content.indexOf(')');
        String value = content.substring(start + 1, end);
        String formatted = value.trim();
        String[] segments = formatted.split(",");
        return List.of(segments);
    }

    private Field parseParameter(String paramString, Lexer lexer) {
        int separator = paramString.indexOf(':');
        String name = paramString.substring(0, separator).trim();
        String typeString = paramString.substring(separator + 1).trim();
        Type type = lexer.resolve(typeString);
        //TODO: parameter flags
        return new InlineField(name, type, Collections.emptyList());
    }
}
