package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.InlineTypePair;
import com.meti.compile.type.TypePair;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.function.Consumer;
import java.util.function.Function;

public class VariableNode implements Node {
	private final String content;

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
		TypePair pair = new InlineTypePair(content, PrimitiveType.Unknown);
		InlineDependents dependents = InlineDependents.of(pair);
		return mapper.apply(dependents);
	}

	@Override
	public <T> T applyToGroup(Function<NodeGroup, T> mapper) {
		return mapper.apply(NodeGroup.Variable);
	}

	@Override
	public Node copy(Dependents dependents) {
		return dependents.streamFields()
				.findFirst()
				.map(field -> field.applyToName(VariableNode::new))
				.orElseThrow();
	}

	public VariableNode(String content) {
		this.content = content;
	}

	@Override
	public String render() {
		return content;
	}
}
