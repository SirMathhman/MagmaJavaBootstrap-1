package com.meti.compile.node.primitive;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.meti.compile.node.EmptyDependents.EmptyDependents;

public class CharNode implements Node {
	private final char value;

	public CharNode(char value) {
		this.value = value;
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		consumer.accept(EmptyDependents());
	}

	@Override
	public Node acceptDependentsChained(Consumer<Dependents> consumer) {
		acceptDependents(consumer);
		return this;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(EmptyDependents());
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Char);
	}

	@Override
	public Node copy(Dependents dependents) {
		return new CharNode(value);
	}

	@Override
	public String render() {
		return "'%s'".formatted(value);
	}
}
