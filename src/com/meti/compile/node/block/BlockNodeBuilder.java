package com.meti.compile.node.block;

import com.meti.compile.node.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockNodeBuilder {
    private final List<Token> children;
    public static final BlockNodeBuilder Identity = new BlockNodeBuilder(Collections.emptyList());

    public BlockNodeBuilder(List<Token> children) {
        this.children = Collections.unmodifiableList(children);
    }

    public BlockNodeBuilder append(Token token) {
        List<Token> newChildren = new ArrayList<>(children);
        newChildren.add(token);
        return new BlockNodeBuilder(newChildren);
    }

    public Token build() {
        return new BlockToken(children);
    }
}
