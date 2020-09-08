package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.scope.DeclareToken;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.type.Field;

public class DeclareProcessor implements Processor {
    private final CallStack stack;
    private final TypeStack typeStack;

    public DeclareProcessor(CallStack stack, TypeStack typeStack) {
        this.stack = stack;
        this.typeStack = typeStack;
    }

    @Override
    public boolean canProcess(TokenGroup group) {
        return TokenGroup.Declare == group;
    }

    @Override
    public Token process(Token token) {
        return token.applyToDependents(Dependents::streamFields)
                .findFirst()
                .applyOrThrow(this::defineInStack);
    }

    public Token defineInStack(Field pair) {
        return stack.define(pair)
                .acceptType(typeStack::push)
                .destructure()
                .withoutEnd()
                .apply(DeclareToken::new);
    }
}