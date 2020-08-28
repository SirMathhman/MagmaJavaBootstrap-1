package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ParentNode implements Node {
	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		consumer.accept(createDependents());
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(createDependents());
	}

	public abstract Dependents createDependents();
}
