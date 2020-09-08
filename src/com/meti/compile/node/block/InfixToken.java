package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.util.CollectiveUtilities;
import com.meti.util.Triad;

import java.util.ArrayList;
import java.util.function.Function;

public class InfixToken extends ParentToken {
    private final Token child0;
    private final Token child1;
    private final Token operator;

    public InfixToken(Token operator, Token child0, Token child1) {
        this.child0 = child0;
        this.child1 = child1;
        this.operator = operator;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChildren(operator, child0, child1);
    }

    //TODO:
    /*
    Complete this node.
    Then complete the parser.
    Then, add a modifier that replaces this node with the proper invocation,
    based on the types of the nodes given.
    Additionally, add native infixes so that C operations can be used as well.
     */

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Infix);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.streamChildren()
                .reduceToMonad(new ArrayList<Token>(), CollectiveUtilities::join)
                .apply(Triad::Triad)
                .apply(InfixToken::new);
    }

    @Override
    public String render() {
        return child0.render() + operator.render() + child1.render();
    }
}
