package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.BlockNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
TODO: add content as field instead of being passed as parameter
instead of canQualify, use validate() instead, removes parameter
also, replace parsing structure with valued nodes such that it's non-recursive
 */
public class BlockRule extends FilteredLexRule {
    @Override
    public boolean canQualify(String content) {
        return content.startsWith("{") &&
                content.endsWith("}");
    }

    @Override
    public Node parseQualified(String content, Lexer lexer) {
        List<String> childStrings = extractChildren(content);
        List<Node> childNodes = parseChildren(lexer, childStrings);
        return new BlockNode(childNodes);
    }

    private static List<Node> parseChildren(Lexer lexer, List<String> children) {
		return children.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(lexer::parse)
                .collect(Collectors.toList());
    }

    public static List<String> extractChildren(String content) {
        String value = content.substring(1, content.length() - 1);
        List<String> results = new ArrayList<>();
        StringBuilder cache = new StringBuilder();
        int length = value.length();
        int depth = 0;
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (';' == c && 0 == depth) {
                results.add(cache.toString());
                cache = new StringBuilder();
            } else if ('}' == c && 1 == depth) {
                cache.append('}');
                depth--;
                results.add(cache.toString());
                cache = new StringBuilder();
            } else {
                if ('{' == c) depth++;
                if ('}' == c) depth--;
                cache.append(c);
            }
        }
        results.add(cache.toString());
        results.removeIf(String::isBlank);
        return results;
    }
}
