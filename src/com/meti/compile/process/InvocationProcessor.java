package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Line;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.function.Predicate;

public class InvocationProcessor implements Processor {
    private final Resolver resolver;

    public InvocationProcessor(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean canProcess(NodeGroup group) {
        return group.matches(NodeGroup.Invocation);
    }

    @Override
    public Node process(Node node) {
        Boolean aBoolean = node.applyToDependents(this::canReturnVoid);
        if (aBoolean) {
            return new Line(node);
        } else {
            return node;
        }
    }

    private boolean canReturnVoid(Dependents dependents) {
        return dependents.streamChildren()
                .findFirst()
                .map(resolver::search)
                .orElseThrow()
                .stream()
                .anyMatch(this::doesReturnVoid);
    }

    private boolean doesReturnVoid(Type type) {
        return type.streamChildren()
                .findFirst()
                .orElseThrow()
                .matches(PrimitiveType.Void);
    }
}
