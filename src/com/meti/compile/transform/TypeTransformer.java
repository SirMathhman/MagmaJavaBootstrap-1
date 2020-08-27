package com.meti.compile.transform;

import com.meti.compile.transform.util.CallStack;
import com.meti.compile.transform.util.TypeStack;

import java.util.stream.Stream;

public class TypeTransformer extends CollectiveTransformer {
	private final CallStack callStack = new CallStack();
	private final Resolver resolver = new Resolver(callStack);
	private final TypeStack typeStack = new TypeStack();

	@Override
	public Stream<Modifier> streamModifiers() {
		return Stream.of(
				new DeclareModifer(callStack, typeStack),
				new InitialModifier(callStack, typeStack),
				new FunctionLoader(typeStack),
				new ReturnModifier(typeStack, resolver)
		);
	}
}