package com.meti.compile.node;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Node extends Renderable {
	void acceptDependents(Consumer<Dependents> consumer);

	<T> T applyToDependents(Function<Dependents, T> mapper);

	<T> T applyToGroup(Function<NodeGroup, T> mapper);

	Node copy(Dependents dependents);
}
