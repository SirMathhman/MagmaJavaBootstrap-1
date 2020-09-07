package com.meti.compile.node;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Line implements Token {
    private final Token child;

    public Line(Token child) {
        this.child = child;
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(InlineDependents.ofChild(child));
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(InlineDependents.ofChild(child));
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Line);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.streamChildrenNatively()
                .findFirst()
                .map(Line::new)
                .orElseThrow();
    }

    @Override
    public String render() {
        return child.render() + ";";
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
