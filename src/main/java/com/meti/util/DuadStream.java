package com.meti.util;

import java.util.function.BiFunction;

public class DuadStream<A, B> {
    private final MonadStream<Duad<A, B>> parent;

    public DuadStream(MonadStream<Duad<A, B>> parent) {
        this.parent = parent;
    }

    public <T> MonadStream<T> map(BiFunction<A, B, T> function) {
        return parent.map(duad -> duad.apply(function));
    }
}
