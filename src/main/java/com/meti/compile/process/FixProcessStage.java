package com.meti.compile.process;

import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.DequeCallStack;
import com.meti.compile.process.util.TypeStack;

import java.util.stream.Stream;

public class FixProcessStage extends CollectiveProcessStage {
    private final CallStack callStack = new DequeCallStack();
    private final TypeStack typeStack = new TypeStack();

    @Override
    public Stream<Preprocessor> streamPreprocessors() {
        return Stream.of(new FunctionPreprocessor(typeStack, callStack));
    }

    @Override
    public Stream<Processor> streamModifiers() {
        return Stream.of(
                new InfixProcessor(callStack)
        );
    }
}
