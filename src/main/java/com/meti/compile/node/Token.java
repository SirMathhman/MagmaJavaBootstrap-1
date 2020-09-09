package com.meti.compile.node;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Token extends Renderable {
    <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function);

    void acceptDependents(Consumer<Dependents> consumer);

    <T> T applyToDependents(Function<Dependents, T> mapper);

    <T> T applyToGroup(Function<TokenGroup, T> mapper);

    Token copy(Dependents dependents);
}
