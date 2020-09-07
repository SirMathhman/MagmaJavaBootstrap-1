package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.FieldBuilder;
import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class InitialNode implements Node {
	private final String name;
	private final Type type;
	private final Node value;

	public InitialNode(String name, Type type, Node value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		//TODO: initial flags
		Field field = new FieldBuilder().withName(name).withType(type).withFlags(Collections.emptyList()).build();
		Dependents dependents = InlineDependents.ofSingleton(field, value);
		consumer.accept(dependents);
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		Field field = new FieldBuilder().withName(name).withType(type).withFlags(Collections.emptyList()).build();
		Dependents dependents = InlineDependents.ofSingleton(field, value);
		return mapper.apply(dependents);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Initial);
	}

	@Override
	public Node copy(Dependents dependents) {
		return dependents.apply(InitialNode::copyImpl);
	}

	public static Node copyImpl(List<Field> fields, List<Node> nodes) {
		Field field = fields.get(0);
		Node value = nodes.get(0);
		return field.apply((name, type) -> new InitialNodeBuilder().withName(name).withType(type).withValue(value).build());
	}

	@Override
	public String render() {
		String renderedType = type.render(name);
		String renderedValue = value.render();
		return "%s=%s;".formatted(renderedType, renderedValue);
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
