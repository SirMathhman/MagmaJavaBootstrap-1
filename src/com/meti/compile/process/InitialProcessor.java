package com.meti.compile.process;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.InitialNodeBuilder;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.List;

public class InitialProcessor implements Processor {
    private final CallStack stack;
    private final TypeStack typeStack;

    public InitialProcessor(CallStack stack, TypeStack typeStack) {
        this.stack = stack;
        this.typeStack = typeStack;
    }

    @Override
    public boolean canProcess(NodeGroup group) {
        return NodeGroup.Initial == group;
    }

    @Override
    public Node process(Node node) {
        return node.applyToDependents(dependents -> dependents.apply(this::construct));
    }

    public Node construct(List<Field> fields, List<Node> nodes) {
        Field pair = fields.get(0);
        Node child = nodes.get(0);
        if (pair.applyToType(type -> PrimitiveType.Implicit == type)) {
            //TODO: implicit types
            throw new UnsupportedOperationException();
        } else {
            return pair.apply(this::define)
                    .withValue(child)
                    .build();
        }
    }

    public InitialNodeBuilder define(Field pair) {
        Field newName = stack.define(pair);
        return newName.apply(this::createNode);
    }

    private InitialNodeBuilder createNode(String name, Type type) {
        return new InitialNodeBuilder()
                .withName(name)
                .withType(type);
    }
}