package com.meti.compile.process;

import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.scope.InitialNodeBuilder;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.process.util.TypeStack;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.Field;
import com.meti.compile.instance.primitive.PrimitiveType;

import java.util.List;

public class InitialProcessor implements Processor {
    private final CallStack stack;
    private final TypeStack typeStack;

    public InitialProcessor(CallStack stack, TypeStack typeStack) {
        this.stack = stack;
        this.typeStack = typeStack;
    }

    @Override
    public boolean canProcess(TokenGroup group) {
        return TokenGroup.Initial == group;
    }

    @Override
    public Token process(Token token) {
        return token.applyToDependents(dependents -> dependents.apply(this::construct));
    }

    public Token construct(List<Field> fields, List<Token> tokens) {
        Field pair = fields.get(0);
        Token child = tokens.get(0);
        if (pair.applyToType(type -> PrimitiveType.Implicit == type)) {
            //TODO: implicit types
            throw new UnsupportedOperationException();
        } else {
            return pair.apply(this::define)
                    .withValue(child)
                    .build();
        }
    }

    public InitialNodeBuilder define(Field pair) {
        Field newName = stack.define(pair);
        return newName.apply(this::createNode);
    }

    private InitialNodeBuilder createNode(String name, Type type) {
        return new InitialNodeBuilder()
                .withName(name)
                .withType(type);
    }
}