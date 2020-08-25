package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.transform.util.CallStack;
import com.meti.compile.transform.util.TypeStack;

import java.util.List;
import java.util.stream.Collectors;

public class MagmaTransformer implements Transformer {
	private final CallStack callStack = new CallStack();
	private final TypeStack typeStack = new TypeStack();
	private final Modifier modifier = new DeclareModifer(callStack, typeStack);

	@Override
	public Node transform(Node node) {
		Dependents dependents = node.applyToDependents(this::transformDependents);
		Node copy = node.copy(dependents);
		if (copy.applyToGroup(modifier::canModify)) {
			return modifier.modify(copy);
		} else {
			return copy;
		}
	}

	private Dependents transformDependents(Dependents dependents) {
		List<Node> children = dependents.streamChildren()
				.map(this::transform)
				.collect(Collectors.toList());
		return dependents.copyChildren(children);
	}
}