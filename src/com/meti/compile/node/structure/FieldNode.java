package com.meti.compile.node.structure;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.block.ParentNode;
import com.meti.util.Unit;

import java.util.function.Function;

public class FieldNode extends ParentNode {
    private final Node child;
    private final String name;

    public FieldNode(Node child, String name) {
        this.child = child;
        this.name = name;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChild(child);
    }

    @Override
    public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
        return mapper.apply(NodeGroup.Field);
    }

    @Override
    public Node copy(Dependents dependents) {
        return dependents.streamChildren()
                .findFirst()
                .map(Unit::new)
                .orElseThrow()
                .with(name)
                .implode(FieldNode::new)
                .complete();
    }

    @Override
    public String render() {
        return "%s.%s".formatted(child.render(), name);
    }
}
