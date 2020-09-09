package com.meti.compile.node.block;

import com.meti.compile.node.Node;
import com.meti.compile.process.util.CallFlag;
import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.Collections;
import java.util.List;

public class FunctionNodeBuilder {
    private final String name;
    private final List<Field> parameters;
    private final Type returnType;
    private final Node value;
    private final List<CallFlag> flags;

    public FunctionNodeBuilder() {
        this(Collections.emptyList(), null, Collections.emptyList(), null, null);
    }

    public FunctionNodeBuilder(List<CallFlag> flags, String name, List<Field> parameters, Type returnType, Node value) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.value = value;
        this.flags = flags;
    }

    public FunctionNodeBuilder withFlags(List<CallFlag> flags) {
        return new FunctionNodeBuilder(flags, name, parameters, returnType, value);
    }

    public FunctionNode build() {
        return new FunctionNode(flags, name, parameters, returnType, value);
    }

    public FunctionNodeBuilder withName(String name) {
        return new FunctionNodeBuilder(flags, name, parameters, returnType, value);
    }

    public FunctionNodeBuilder withReturnType(Type returnType) {
        return new FunctionNodeBuilder(flags, name, parameters, returnType, value);
    }

    public FunctionNodeBuilder withParameters(List<Field> parameters) {
        return new FunctionNodeBuilder(flags, name, parameters, returnType, value);
    }

    public FunctionNodeBuilder withValue(Node value) {
        return new FunctionNodeBuilder(flags, name, parameters, returnType, value);
    }
}