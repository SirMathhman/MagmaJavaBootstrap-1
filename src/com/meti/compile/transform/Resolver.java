package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.VariableNode;
import com.meti.compile.transform.util.CallStack;
import com.meti.compile.type.Type;

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
				.orElseThrow();
	}
}
