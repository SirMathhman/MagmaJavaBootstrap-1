package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.InlineTypePair;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.List;
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
	public Node acceptDependents(Consumer<Dependents> consumer) {
		applyToDependents((Function<Dependents, Void>) dependents -> {
			consumer.accept(dependents);
			return null;
		});
		return this;
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		TypePair field = new InlineTypePair(name, type);
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

	public static Node copyImpl(List<TypePair> typePairs, List<Node> nodes) {
		TypePair field = typePairs.get(0);
		Node value = nodes.get(0);
		return field.apply((name, type) -> new InitialNodeBuilder().withName(name).withType(type).withValue(value).build());
	}

	@Override
	public String render() {
		String renderedType = type.render(name);
		String renderedValue = value.render();
		return "%s=%s;".formatted(renderedType, renderedValue);
	}
}
