package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ReturnNode implements Node {
	private final Node value;

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		consumer.accept(InlineDependents.ofChild(value));
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(InlineDependents.ofChild(value));
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Return);
	}

	@Override
	public Node copy(Dependents dependents) {
		return dependents.streamChildren()
				.findFirst().map(ReturnNode::new)
				.orElseThrow();
	}

	public ReturnNode(Node value) {
		this.value = value;
	}

	@Override
	public String render() {
		String renderedValue = value.render();
		return "return %s;".formatted(renderedValue);
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
