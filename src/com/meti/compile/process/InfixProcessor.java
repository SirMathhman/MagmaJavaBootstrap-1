package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.block.InfixToken;
import com.meti.compile.node.block.InvocationToken;
import com.meti.compile.node.block.ParentToken;
import com.meti.compile.process.util.CallFlag;
import com.meti.compile.process.util.CallStack;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InfixProcessor implements Processor {
    private final CallStack callStack;

    public InfixProcessor(CallStack callStack) {
        this.callStack = callStack;
    }

    @Override
    public boolean canProcess(TokenGroup group) {
        return TokenGroup.Infix.matches(group);
    }

    @Override
    public Token process(Token token) {
        return token.applyToDependents((Function<Dependents, Token>) this::processDependents);
    }

    private ParentToken processDependents(Dependents dependents) {
        List<Token> children = dependents.streamChildren().collect(Collectors.toList());
        Token operator = children.get(0);
        Token value0 = children.get(1);
        Token value1 = children.get(2);
        if (operator.applyToGroup(TokenGroup.Variable::matches)) {
            boolean isNativeInfix = isNativeInfix(operator);
            if (isNativeInfix) {
                return new InfixToken(operator, value0, value1);
            }
        }
        return new InvocationToken(operator, List.of(value0, value1));
    }

    private boolean isNativeInfix(Token operator) {
        return operator.applyToContent(String.class, callStack::flags)
                .flatMap(Function.identity())
                .orElseThrow(() -> new ProcessException("%s is not defined in %s, cannot resolve infix.".formatted(operator.render(), callStack)))
                .containsAll(Set.of(CallFlag.NATIVE, CallFlag.INFIX));
    }
}
