package com.meti.compile.transform;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.transform.util.TypeStack;
import com.meti.compile.type.Type;

public class FunctionLoader implements Loader {
	private final TypeStack stack;

	public FunctionLoader(TypeStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean canLoad(NodeGroup group) {
		return group.matches(NodeGroup.Function);
	}

	@Override
	public void load(Node node) {
		node.acceptDependents(this::process);
	}

	public void process(Dependents dependents) {
		dependents.streamFields()
				.findFirst()
				.map(typePair -> typePair.applyToType(FunctionLoader::findReturnType))
				.ifPresent(stack::push);
	}

	public static Type findReturnType(Type type) {
		return type.streamChildren()
				.findFirst()
				.orElseThrow(() -> createMalformedType(type));
	}

	public static MalformedException createMalformedType(Type type) {
		String message = "Function type '%s' has no return type.".formatted(type);
		return new MalformedException(message);
	}
}
