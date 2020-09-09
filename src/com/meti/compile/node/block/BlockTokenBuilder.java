package com.meti.compile.node.block;

import com.meti.compile.node.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockTokenBuilder {
    private final List<Token> children;
    public static final BlockTokenBuilder Identity = new BlockTokenBuilder(Collections.emptyList());

    public BlockTokenBuilder() {
        this(Collections.emptyList());
    }

    public BlockTokenBuilder(List<Token> children) {
        this.children = Collections.unmodifiableList(children);
    }

    public BlockTokenBuilder append(Token token) {
        List<Token> newChildren = new ArrayList<>(children);
        newChildren.add(token);
        return new BlockTokenBuilder(newChildren);
    }

    public Token build() {
        return new BlockToken(children);
    }
}
