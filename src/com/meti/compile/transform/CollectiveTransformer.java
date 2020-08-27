package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CollectiveTransformer implements Transformer {
	@Override
	public Node transform(Node node) {
		load(node);
		Dependents dependents = node.applyToDependents(this::transformDependents);
		Node copy = node.copy(dependents);
		Optional<Node> transformOptional = transformOptionally(copy);
		return transformOptional.orElse(copy);
	}

	public void load(Node node) {
		streamLoaders()
				.filter(loader -> node.applyToGroup(loader::canLoad))
				.forEach(loader -> loader.load(node));
	}

	public abstract Stream<Modifier> streamModifiers();

	public Optional<Node> transformOptionally(Node copy) {
		return streamModifiers()
				.filter(modifier1 -> copy.applyToGroup(modifier1::canModify))
				.map(modifier1 -> modifier1.modify(copy))
				.findFirst();
	}

	private Dependents transformDependents(Dependents dependents) {
		List<Node> children = dependents.streamChildren()
				.map(this::transform)
				.collect(Collectors.toList());
		return dependents.copyChildren(children);
	}

	public abstract Stream<Loader> streamLoaders();
}
