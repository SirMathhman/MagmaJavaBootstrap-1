package com.meti.feature;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.DeclareNode;
import com.meti.compile.type.Type;
import com.meti.compile.util.CallStack;

public class DeclareModifer implements Modifier {
	private final CallStack stack;
	private final TypeStack typeStack;

	public DeclareModifer(CallStack stack, TypeStack typeStack) {
		this.stack = stack;
		this.typeStack = typeStack;
	}

	@Override
	public boolean canModify(NodeGroup nodeGroup) {
		return NodeGroup.Declare == nodeGroup;
	}

	@Override
	public Node modify(Node copy) {
		return copy.applyToDependents(Dependents::streamFields)
				.findFirst()
				.orElseThrow()
				.apply(this::defineInStack);
	}

	public Node defineInStack(String s, Type type) {
		String newName = stack.define(s, type);
		typeStack.push(type);
		return new DeclareNode(newName, type);
	}
}