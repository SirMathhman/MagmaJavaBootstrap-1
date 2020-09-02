package com.meti.compile.node.structure;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.block.ParentNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StructDeclareNode extends ParentNode {
    private final String name;
    private final List<Node> arguments;

    public StructDeclareNode(String name, List<Node> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChildren(arguments);
    }

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.StructDeclare);
    }

    @Override
    public Node copy(Dependents dependents) {
        return dependents.streamChildren().reduce(createBuilder(),
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
                .map(Node::render)
                .collect(Collectors.joining(",", "{", "}"));
    }

    public static class Builder {
        private final String name;
        private final List<Node> children;

        public <T> T apply(Function<Builder, T> function) {
            return function.apply(this);
        }

        public Builder() {
            this(null, new ArrayList<>());
        }

        public Builder(String name, List<Node> children) {
            this.name = name;
            this.children = children;
        }

        public Builder withName(String name) {
            return new Builder(name, children);
        }

        public Builder withChild(Node child) {
            this.children.add(child);
            return this;
        }

        public Node build() {
            return new StructDeclareNode(name, children);
        }
    }
}
