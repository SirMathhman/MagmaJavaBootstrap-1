package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.block.InfixNode;
import com.meti.compile.node.block.InvocationNode;
import com.meti.compile.node.block.ParentNode;
import com.meti.compile.process.util.CallFlag;
import com.meti.compile.process.util.CallStack;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InfixProcessor implements Processor {
    private final CallStack callStack;

    public InfixProcessor(CallStack callStack) {
        this.callStack = callStack;
    }

    @Override
    public boolean canProcess(NodeGroup group) {
        return NodeGroup.Infix.matches(group);
    }

    @Override
    public Node process(Node node) {
        return node.applyToDependents((Function<Dependents, Node>) this::processDependents);
    }

    private ParentNode processDependents(Dependents dependents) {
        List<Node> children = dependents.streamChildren().collect(Collectors.toList());
        Node operator = children.get(0);
        Node value0 = children.get(1);
        Node value1 = children.get(2);
        if (operator.applyToGroup(NodeGroup.Variable::matches)) {
            boolean isNativeInfix = isNativeInfix(operator);
            if (isNativeInfix) {
                return new InfixNode(operator, value0, value1);
            }
        }
        return new InvocationNode(operator, List.of(value0, value1));
    }

    private boolean isNativeInfix(Node operator) {
        return operator.applyToContent(String.class, callStack::flags)
                .flatMap(Function.identity())
                .orElseThrow(() -> new ProcessException("%s is not defined in %s, cannot resolve infix.".formatted(operator.render(), callStack)))
                .containsAll(Set.of(CallFlag.NATIVE, CallFlag.INFIX));
    }
}
