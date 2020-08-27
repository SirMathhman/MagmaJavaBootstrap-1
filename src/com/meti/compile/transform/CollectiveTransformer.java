package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CollectiveTransformer implements Transformer {
	public abstract Stream<Modifier> streamModifiers();

	@Override
	public Node transform(Node node) {
		Dependents dependents = node.applyToDependents(this::transformDependents);
		Node copy = node.copy(dependents);
		return streamModifiers()
				.filter(modifier1 -> copy.applyToGroup(modifier1::canModify))
				.map(modifier1 -> modifier1.modify(copy))
				.findFirst()
				.orElse(node);
	}

	private Dependents transformDependents(Dependents dependents) {
		List<Node> children = dependents.streamChildren()
				.map(this::transform)
				.collect(Collectors.toList());
		return dependents.copyChildren(children);
	}
}
