package com.meti.compile.node;

import com.meti.util.Option;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Token extends Renderable {
    default <T, R> Option<R> applyToContentOptionally(Class<? extends T> clazz, Function<T, R> function) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function);

    void acceptDependents(Consumer<Dependents> consumer);

    <T> T applyToDependents(Function<Dependents, T> mapper);

    <T> T applyToGroup(Function<TokenGroup, T> mapper);

    Token copy(Dependents dependents);
}
