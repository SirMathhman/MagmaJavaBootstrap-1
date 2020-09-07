package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Line;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class InvocationProcessor implements Processor {
    private final Resolver resolver;

    public InvocationProcessor(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean canProcess(TokenGroup group) {
        return group.matches(TokenGroup.Invocation);
    }

    @Override
    public Token process(Token token) {
        Boolean aBoolean = token.applyToDependents(this::canReturnVoid);
        if (aBoolean) {
            return new Line(token);
        } else {
            return token;
        }
    }

    private boolean canReturnVoid(Dependents dependents) {
        return dependents.streamChildrenNatively()
                .findFirst()
                .map(resolver::search)
                .orElseThrow()
                .stream()
                .anyMatch(this::doesReturnVoid);
    }

    private boolean doesReturnVoid(Type type) {
        return type.streamChildren()
                .findFirst()
                .orElseThrow()
                .matches(PrimitiveType.Void);
    }
}
