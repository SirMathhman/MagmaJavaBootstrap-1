package com.meti.compile.transform;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.scope.InitialNodeBuilder;
import com.meti.compile.transform.util.CallStack;
import com.meti.compile.transform.util.TypeStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.List;

public class InitialModifier implements Modifier {
	private final CallStack stack;
	private final TypeStack typeStack;

	public InitialModifier(CallStack stack, TypeStack typeStack) {
		this.stack = stack;
		this.typeStack = typeStack;
	}

	@Override
	public boolean canModify(NodeGroup nodeGroup) {
		return NodeGroup.Initial == nodeGroup;
	}

	@Override
	public Node modify(Node copy) {
		return copy.applyToDependents(dependents -> dependents.apply(this::construct));
	}

	public Node construct(List<TypePair> typePairs, List<Node> nodes) {
		TypePair pair = typePairs.get(0);
		Node child = nodes.get(0);
		if (pair.applyToType(type -> PrimitiveType.Implicit == type)) {
			throw new UnsupportedOperationException();
		} else {
			return pair.apply(this::define)
					.withValue(child)
					.build();
		}
	}

	public InitialNodeBuilder define(String name, Type type) {
		String newName = stack.define(name, type);
		typeStack.push(type);
		return new InitialNodeBuilder()
				.withName(newName)
				.withType(type);
	}
}