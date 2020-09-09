package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionPreprocessor implements Preprocessor {
    private final TypeStack typeStack;
    private final CallStack callStack;

    public FunctionPreprocessor(TypeStack typeStack, CallStack callStack) {
        this.typeStack = typeStack;
        this.callStack = callStack;
    }

    @Override
    public boolean canPreprocess(NodeGroup group) {
        return group.matches(NodeGroup.Function);
    }

    @Override
    public Node preprocess(Node node) {
        Dependents dependents = node.applyToDependents(this::processFunction);
        return node.copy(dependents);
    }

    public Dependents processFunction(Dependents oldDependents) {
        List<Field> fields = oldDependents.streamFields().collect(Collectors.toList());
        Field oldFirst = fields.get(0);
        Type type = oldFirst.applyToType(this::findReturnType);
        typeStack.push(type);
        List<Field> oldParameters = fields.subList(1, fields.size());
        Field newFirst = callStack.define(oldFirst);
        List<Field> newParameters = callStack.enter(oldParameters);
        List<Field> newFields = new ArrayList<>();
        newFields.add(newFirst);
        newFields.addAll(newParameters);
        return oldDependents.copyFields(newFields);
    }

    public Type findReturnType(Type type) {
        return type.streamChildren()
                .findFirst()
                .orElseThrow(() -> createMalformedType(type));
    }

    public static MalformedException createMalformedType(Type type) {
        String message = "Function type '%s' has no return type.".formatted(type);
        return new MalformedException(message);
    }
}
