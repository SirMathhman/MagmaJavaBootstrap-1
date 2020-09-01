package com.meti.compile.process;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.node.block.ReturnNode;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class ReturnProcessor implements Processor {
	private final Resolver resolver;
	private final TypeStack typeStack;

	public ReturnProcessor(TypeStack typeStack, Resolver resolver) {
		this.typeStack = typeStack;
		this.resolver = resolver;
	}

	@Override
	public boolean canProcess(NodeGroup group) {
		return group.matches(NodeGroup.Return);
	}

	@Override
	public Node process(Node node) {
		return typeStack.peek()
				.filter(type -> PrimitiveType.Implicit != type)
				.map(type -> findNewValue(node, type))
				.map(ReturnNode::new)
				.orElseThrow(this::createEmptyError);
	}

	public IllegalStateException createEmptyError() {
		String message = "%s doesn't seem to have any types in the queue.".formatted(typeStack);
		return new IllegalStateException(message);
	}

	public Node findNewValue(Node node, Type type) {
		return node.applyToDependents(dependents -> dependents.streamChildren()
				.findFirst()
				.map(value -> resolver.force(value, type))
				.orElseThrow());
	}
}
