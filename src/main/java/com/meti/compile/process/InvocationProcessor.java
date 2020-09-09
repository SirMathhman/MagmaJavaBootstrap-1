package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Line;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.primitive.PrimitiveType;

import java.util.Collection;

import static com.meti.util.Some.Some;

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
        return Some(token)
                .filter(t -> t.applyToDependents(this::canReturnVoid))
                .applyOrElse(Line::new, token);
    }

    private boolean canReturnVoid(Dependents dependents) {
        return dependents.streamChildren()
                .findFirst()
                .map(resolver::search)
                .applyOrThrow(Collection::stream)
                .anyMatch(this::doesReturnVoid);
    }

    private boolean doesReturnVoid(Type type) {
        return type.streamChildren().findFirst()
                .applyOrThrow(PrimitiveType.Void::matches);
    }
}
