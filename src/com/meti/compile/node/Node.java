package com.meti.compile.node;

import java.util.function.Function;

public interface Node extends Renderable {
	<T> T applyToDependents(Function<Dependents, T> mapper);

	<T> T applyToGroup(Function<NodeGroup, T> mapper);

	Node copy(Dependents dependents);
}
