package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.scope.VariableToken;
import com.meti.compile.process.util.CallStack;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.primitive.PrimitiveType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.node.TokenGroup.Int;
import static com.meti.compile.node.TokenGroup.Variable;

public class Resolver {
    private final CallStack callStack;
    private final NodeResolveRule nodeResolveRule = new InvocationResolveRule();

    public Resolver(CallStack callStack) {
        this.callStack = callStack;
    }

    public Token force(Token token, Type expectedType) {
        if (token.applyToGroup(TokenGroup.Infix::matches)) {
            Dependents dependents1 = token.applyToDependents(dependents -> nodeResolveRule.resolve(
                    dependents, expectedType,
                    this));
            return token.copy(dependents1);
        } else if (token.applyToGroup(TokenGroup.Invocation::matches)) {
            Dependents dependents1 = token.applyToDependents(dependents -> nodeResolveRule.resolve(
                    dependents, expectedType,
                    this));
            return token.copy(dependents1);
        } else if (token.applyToGroup(Variable::matches)) {
            return token.applyToDependents(dependents -> mapToVariable(dependents, expectedType))
                    .orElseThrow(() -> createNoContent(token));
        } else if (token.applyToGroup(TokenGroup.Int::matches) && PrimitiveType.Int == expectedType) {
            return token;
        } else {
            String message = "%s doesn't seem to be a type of %s".formatted(token, expectedType);
            throw new IllegalArgumentException(message);
        }
    }

    public Optional<Token> mapToVariable(Dependents dependents, Type type) {
        return dependents.streamFieldsNatively()
                .findFirst()
                .map(typePair -> typePair.applyToName(name -> mapToAlias(name, type)));
    }

    public static MalformedException createNoContent(Token token) {
        String message = "Variable %s has no content.".formatted(token);
        return new MalformedException(message);
    }

    public Token mapToAlias(String s, Type type) {
        return callStack.lookup(s, type)
                .map(VariableToken::new)
                .orElseThrow(() -> createUndefinedError(s));
    }

    public IllegalArgumentException createUndefinedError(String s) {
        String message = "Variable with name \"%s\" is not defined in scope: %s".formatted(s, callStack);
        return new IllegalArgumentException(message);
    }

    public List<Type> search(Token token) {
        if (token.applyToGroup(Variable::matches)) {
            return token.applyToDependents(this::searchVariable);
        } else if (token.applyToGroup(Int::matches)) {
            return Collections.singletonList(PrimitiveType.Int);
        }
        throw new IllegalArgumentException("Failed to search for valid types of: " + token);
    }

    public List<Type> searchVariable(Dependents dependents) {
        return dependents.streamFieldsNatively()
                .findFirst()
                .orElseThrow()
                .applyToName(this::lookupExceptionally);
    }

    private List<Type> lookupExceptionally(String s) {
        if (callStack.isDefined(s)) {
            return callStack.lookup(s);
        } else {
            throw createUndefinedError(s);
        }
    }
}
