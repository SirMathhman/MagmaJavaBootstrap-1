package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.DeclareNode;
import com.meti.compile.transform.util.CallStack;
import com.meti.compile.transform.util.TypeStack;
import com.meti.compile.type.Type;

public class DeclareModifer implements Modifier {
	private final CallStack stack;
	private final TypeStack typeStack;

	public DeclareModifer(CallStack stack, TypeStack typeStack) {
		this.stack = stack;
		this.typeStack = typeStack;
	}

	@Override
	public boolean canModify(NodeGroup group) {
		return NodeGroup.Declare == group;
	}

	@Override
	public Node modify(Node node) {
		return node.applyToDependents(Dependents::streamFields)
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