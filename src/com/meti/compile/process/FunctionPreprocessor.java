package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.DependentsBuilder;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.function.Function;

import static com.meti.util.Monad.Monad;

public class FunctionPreprocessor implements Preprocessor {
    private final TypeStack typeStack;
    private final CallStack callStack;

    public FunctionPreprocessor(TypeStack typeStack, CallStack callStack) {
        this.typeStack = typeStack;
        this.callStack = callStack;
    }

    @Override
    public boolean canPreprocess(TokenGroup group) {
        return group.matches(TokenGroup.Function);
    }

    @Override
    public Token preprocess(Token token) {
        return Monad(token)
                .append((Function<Dependents, Dependents>) this::processFunction)
                .replaceEnd(Token::applyToDependents)
                .apply(Token::copy);
    }

    public Dependents processFunction(Dependents oldDependents) {
        /*
        This function eventually needs to be rephrased in which all fields are all immutable.
        The problem lies within "acceptType" and "callStack.enter", in which the fields
        callStack and typeStack are being changed internally, whereas they should really be copied.
         */
        Field newProperties = oldDependents.applyToProperties(field1 -> {
            field1.acceptType(FunctionPreprocessor.this::pushReturnToStack);
            return callStack.define(field1);
        });
        Dependents dependents = oldDependents.copyProperties(newProperties);
        DependentsBuilder builder = dependents.withoutFields();
        callStack.enter();
        return oldDependents.streamFields()
                .map(callStack::define)
                .reduce(builder, DependentsBuilder::append)
                .build();
    }

    private void pushReturnToStack(Type type1) {
        type1.streamChildren()
                .findFirst()
                .acceptOrThrow(typeStack::push, createMalformedType(type1));
    }

    public static MalformedException createMalformedType(Type type) {
        String message = "Function type '%s' has no return type.".formatted(type);
        return new MalformedException(message);
    }
}
