package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.block.InfixToken;
import com.meti.compile.node.block.InvocationToken;
import com.meti.compile.node.block.ParentToken;
import com.meti.compile.process.util.CallFlag;
import com.meti.compile.process.util.CallStack;
import com.meti.util.CollectiveUtilities;
import com.meti.util.Triad;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.meti.compile.node.TokenGroup.Variable;
import static com.meti.util.Triad.Triad;

public class InfixProcessor implements Processor {
    public static final Set<CallFlag> InfixFlags = Set.of(CallFlag.NATIVE, CallFlag.INFIX);
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
        return dependents.streamChildren()
                .reduceToMonad(new ArrayList<Token>(), CollectiveUtilities::join)
                .apply(Triad::Triad)
                .applyByStart(this::isValue, this::processVariable, InvocationToken::new);
    }

    private Boolean isValue(Token operator) {
        return operator.applyToGroup(Variable::matches);
    }

    private ParentToken processVariable(Token token, Token token2, Token token3) {
        return Triad(token, token2, token3).applyByStart(this::isNativeInfix, InfixToken::new, InvocationToken::new);
    }

    private boolean isNativeInfix(Token operator) {
        return operator.applyToContentOptionally(String.class, callStack::flagsOptionally)
                .flatten(listOption -> listOption)
                .with(InfixFlags)
                .applyAllOrThrow(List::containsAll);
    }
}