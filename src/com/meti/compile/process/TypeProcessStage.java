package com.meti.compile.process;

import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;

import java.util.stream.Stream;

public class TypeProcessStage extends CollectiveProcessStage {
	private final CallStack callStack = new CallStack();
	private final Resolver resolver = new Resolver(callStack);
	private final TypeStack typeStack = new TypeStack();

	@Override
	public Stream<Preprocessor> streamLoaders() {
		return Stream.of(new FunctionPreprocessor(typeStack, callStack));
	}

	@Override
	public Stream<Processor> streamModifiers() {
		return Stream.of(
				new InvocationProcessor(resolver),
				new FunctionProcessor(callStack),
				new DeclareProcessor(callStack, typeStack),
				new InitialProcessor(callStack, typeStack),
				new ReturnProcessor(typeStack, resolver)
		);
	}
}