package com.meti.compile.node.structure;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.block.ParentToken;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StructDeclareToken extends ParentToken {
    private final String name;
    private final List<Token> arguments;

    public StructDeclareToken(String name, List<Token> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChildren(arguments);
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.StructDeclare);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.streamChildrenNatively().reduce(createBuilder(),
                Builder::withChild,
                (oldBuilder, newBuilder) -> newBuilder)
                .build();
    }

    private Builder createBuilder() {
        return new Builder().withName(name);
    }

    @Override
    public String render() {
        return arguments.stream()
                .map(Token::render)
                .collect(Collectors.joining(",", "{", "}"));
    }

    public static class Builder {
        private final String name;
        private final List<Token> children;

        public <T> T apply(Function<Builder, T> function) {
            return function.apply(this);
        }

        public Builder() {
            this(null, new ArrayList<>());
        }

        public Builder(String name, List<Token> children) {
            this.name = name;
            this.children = children;
        }

        public Builder withName(String name) {
            return new Builder(name, children);
        }

        public Builder withChild(Token child) {
            this.children.add(child);
            return this;
        }

        public Token build() {
            return new StructDeclareToken(name, children);
        }
    }
}
