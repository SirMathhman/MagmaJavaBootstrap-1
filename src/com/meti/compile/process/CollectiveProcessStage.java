package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CollectiveProcessStage implements ProcessStage {
	public Node load(Node node) {
		return streamPreprocessors()
				.filter(preprocessor -> node.applyToGroup(preprocessor::canPreprocess))
				.map(preprocessor -> preprocessor.preprocess(node))
				.findFirst()
				.orElse(node);
	}

	@Override
	public Node process(Node node) {
		Node loaded = load(node);
		Dependents dependents = loaded.applyToDependents(this::transformDependents);
		Node copy = loaded.copy(dependents);
		Optional<Node> transformOptional = transformOptionally(copy);
		return transformOptional.orElse(copy);
	}

	public abstract Stream<Preprocessor> streamPreprocessors();

	public Optional<Node> transformOptionally(Node copy) {
		return streamModifiers()
				.filter(modifier1 -> copy.applyToGroup(modifier1::canProcess))
				.map(modifier1 -> modifier1.process(copy))
				.findFirst();
	}

	public abstract Stream<Processor> streamModifiers();

	private Dependents transformDependents(Dependents dependents) {
		List<Node> children = dependents.streamChildren()
				.map(this::process)
				.collect(Collectors.toList());
		return dependents.copyChildren(children);
	}
}
