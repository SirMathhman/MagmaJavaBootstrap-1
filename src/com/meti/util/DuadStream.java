package com.meti.util;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public class DuadStream<A, B> {
    private final Stream<Duad<A, B>> stream;

    public DuadStream(Stream<Duad<A, B>> stream) {
        this.stream = stream;
    }

    public <T> MonadStream<T> map(BiFunction<A, B, T> function) {
        return new MonadStream<>(stream.map(duad -> duad.map(function)));
    }
}
