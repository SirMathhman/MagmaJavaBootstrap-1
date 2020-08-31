package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.type.TypePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionProcessor implements Processor {
    private final CallStack callStack;

    public FunctionProcessor(CallStack callStack) {
        this.callStack = callStack;
    }

    @Override
    public boolean canProcess(NodeGroup group) {
        return group.matches(NodeGroup.Function);
    }

    @Override
    public Node process(Node node) {
        return node.applyToDependents(dependents -> define(dependents, node));
    }

    public Node define(Dependents dependents, Node node) {
        List<TypePair> newFields = createFields(dependents);
        Dependents newDependents = dependents.copyFields(newFields);
        return node.copy(newDependents);
    }

    public List<TypePair> createFields(Dependents dependents) {
        List<TypePair> oldFields = dependents.streamFields()
                .collect(Collectors.toList());
        TypePair typePair = oldFields.get(0);
        TypePair pair = typePair.apply(callStack::define);
        List<TypePair> newFields = new ArrayList<>(Collections.singletonList(pair));
        List<TypePair> subFields = oldFields.subList(1, oldFields.size());
        newFields.addAll(subFields);
        return newFields;
    }
}
