package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.VariableNode;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.node.NodeGroup.Int;
import static com.meti.compile.node.NodeGroup.Variable;

public class Resolver {
    private final CallStack callStack;
    private final NodeResolveRule nodeResolveRule = new InvocationResolveRule();

    public Resolver(CallStack callStack) {
        this.callStack = callStack;
    }

    public Node force(Node node, Type expectedType) {
        if (node.applyToGroup(NodeGroup.Infix::matches)) {
            Dependents dependents1 = node.applyToDependents(dependents -> nodeResolveRule.resolve(
                    dependents, expectedType,
                    this));
            return node.copy(dependents1);
        } else if (node.applyToGroup(NodeGroup.Invocation::matches)) {
            Dependents dependents1 = node.applyToDependents(dependents -> nodeResolveRule.resolve(
                    dependents, expectedType,
                    this));
            return node.copy(dependents1);
        } else if (node.applyToGroup(Variable::matches)) {
            return node.applyToDependents(dependents -> mapToVariable(dependents, expectedType))
                    .orElseThrow(() -> createNoContent(node));
        } else if (node.applyToGroup(NodeGroup.Int::matches) && PrimitiveType.Int == expectedType) {
            return node;
        } else {
            String message = "%s doesn't seem to be a type of %s".formatted(node, expectedType);
            throw new IllegalArgumentException(message);
        }
    }

    public Optional<Node> mapToVariable(Dependents dependents, Type type) {
        return dependents.streamFields()
                .findFirst()
                .map(typePair -> typePair.applyToName(name -> mapToAlias(name, type)));
    }

    public static MalformedException createNoContent(Node node) {
        String message = "Variable %s has no content.".formatted(node);
        return new MalformedException(message);
    }

    public Node mapToAlias(String s, Type type) {
        return callStack.lookup(s, type)
                .map(VariableNode::new)
                .orElseThrow(() -> createUndefinedError(s));
    }

    public IllegalArgumentException createUndefinedError(String s) {
        String message = "Variable with name \"%s\" is not defined in scope: %s".formatted(s, callStack);
        return new IllegalArgumentException(message);
    }

    public List<Type> search(Node node) {
        if (node.applyToGroup(Variable::matches)) {
            return node.applyToDependents(this::searchVariable);
        } else if (node.applyToGroup(Int::matches)) {
            return Collections.singletonList(PrimitiveType.Int);
        }
        throw new IllegalArgumentException("Failed to search for valid types of: " + node);
    }

    public List<Type> searchVariable(Dependents dependents) {
        return dependents.streamFields()
                .findFirst()
                .orElseThrow()
                .applyToName(this::lookupExceptionally);
    }

    private List<Type> lookupExceptionally(String s) {
        if (callStack.isDefined(s)) {
            return callStack.lookup(s);
        } else {
            throw createUndefinedError(s);
        }
    }
}
