package com.meti.util;

import java.util.Collection;
import java.util.stream.Stream;

public class MonadStream<T> {
    private final Stream<Monad<T>> monadStream;

    public static <R> MonadStream<R> Stream(Collection<R> collection) {
        return Stream(collection.stream());
    }

    private static <R> MonadStream<R> Stream(Stream<R> stream) {
        return new MonadStream<>(stream.map(Monad::new));
    }

    public MonadStream(Stream<Monad<T>> monadStream) {
        this.monadStream = monadStream;
    }
}
