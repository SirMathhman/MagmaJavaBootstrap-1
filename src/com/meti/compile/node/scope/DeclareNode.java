package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Type;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class DeclareNode implements Node {
	private final String name;
	private final Type type;

	public DeclareNode(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(null);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node copy(Dependents dependents) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String render() {
		return "%s;".formatted(type.render(name));
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
