package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.VariableNode;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.Optional;

public class Resolver {
	private final CallStack callStack;

	public Resolver(CallStack callStack) {
		this.callStack = callStack;
	}

	public Node force(Node node, Type type) {
		if (node.applyToGroup(NodeGroup.Variable::matches)) {
			return node.applyToDependents(dependents -> mapToVariable(dependents, type))
					.orElseThrow(() -> createNoContent(node));
		} else if (node.applyToGroup(NodeGroup.Int::matches) && PrimitiveType.Int == type) {
			return node;
		} else {
			throw new IllegalArgumentException("Cannot force " + node + " to type of " + type);
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
}
