package com.meti.compile.process;

import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.block.ReturnToken;
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
	public boolean canProcess(TokenGroup group) {
		return group.matches(TokenGroup.Return);
	}

	@Override
	public Token process(Token token) {
		return typeStack.peek()
				.filter(type -> PrimitiveType.Implicit != type)
				.map(type -> findNewValue(token, type))
				.map(ReturnToken::new)
				.orElseThrow(this::createEmptyError);
	}

	public IllegalStateException createEmptyError() {
		String message = "%s doesn't seem to have any types in the queue.".formatted(typeStack);
		return new IllegalStateException(message);
	}

	public Token findNewValue(Token token, Type type) {
		return token.applyToDependents(dependents -> dependents.streamChildren()
				.findFirst()
				.map(value -> resolver.force(value, type))
				.orElseThrow());
	}
}
