package com.meti.compile.node.structure;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.node.block.ParentToken;
import com.meti.util.Monad;

import java.util.function.Function;

public class FieldToken extends ParentToken {
    private final Token child;
    private final String name;

    public FieldToken(Token child, String name) {
        this.child = child;
        this.name = name;
    }

    @Override
    public Dependents createDependents() {
        return InlineDependents.ofChild(child);
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Field);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.streamChildrenNatively()
                .findFirst()
                .map(Monad::Monad)
                .orElseThrow()
                .with(name)
                .map(FieldToken::new)
                .complete();
    }

    @Override
    public String render() {
        return "%s.%s".formatted(child.render(), name);
    }
}
