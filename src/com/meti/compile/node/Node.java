package com.meti.compile.node;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Node extends Renderable {
	@Deprecated
	void acceptDependents(Consumer<Dependents> consumer);

	Node acceptDependentsChained(Consumer<Dependents> consumer);

	<T> T applyToDependents(Function<Dependents, T> mapper);

	<T> T applyToGroup(Function<NodeGroup, T> mapper);

	Node copy(Dependents dependents);
}
