package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.InvocationNode;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        return content.contains("(") && content.endsWith(")");
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        Node caller = parseCaller(content, lexer);
        List<String> argStrings = extractArgStrings(content);
        List<Node> arguments = parseArguments(lexer, argStrings);
        return new InvocationNode(caller, arguments);
    }

    private Node parseCaller(String content, Lexer lexer) {
        int callerEnd = content.indexOf('(');
        String value = content.substring(0, callerEnd);
        String formatted = value.trim();
        return lexer.parse(formatted);
    }

    private List<String> extractArgStrings(String content) {
        int length = content.length();
        int argStart = content.indexOf('(');
        String value = content.substring(argStart + 1, length - 1);
        String formatted = value.trim();
        String[] args = formatted.split(",");
        return List.of(args);
    }

    private List<Node> parseArguments(Lexer lexer, List<String> argStrings) {
        return argStrings.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(lexer::parse)
                .collect(Collectors.toList());
    }
}
