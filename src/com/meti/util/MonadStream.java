package com.meti.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class MonadStream<T> {
    private final Stream<Monad<T>> stream;

    public static <R> MonadStream<R> Stream(Collection<R> collection) {
        return Stream(collection.stream());
    }

    private static <R> MonadStream<R> Stream(Stream<R> stream) {
        return new MonadStream<>(stream.map(Monad::new));
    }

    public MonadStream(Stream<Monad<T>> stream) {
        this.stream = stream;
    }

    public static <T> MonadStream<T> Empty() {
        return new MonadStream<>(Stream.empty());
    }

    public <R> DuadStream<T, R> with(R value) {
        return new DuadStream<>(stream.map(tMonad -> tMonad.with(value)));
    }

    public <R> MonadStream<R> map(Function<T, R> function) {
        return new MonadStream<>(stream.map(monad -> monad.map(function)));
    }

    public <R> MonadOption<R> applyFirst(Function<T, R> function) {
        return stream.findFirst()
                .map(Some::Some)
                .orElseGet(None::None)
                .with(function)
                .map(Monad::apply);
    }

    public <R> MonadStream<R> flatMap(Function<T, MonadStream<R>> function) {
        return new MonadStream<>(stream.map(tMonad -> tMonad.apply(function))
                .flatMap(MonadStream::toNative));
    }

    private Stream<Monad<T>> toNative() {
        return stream;
    }

    public MonadOption<T> findFirst() {
        return stream.findFirst()
                .map(Monad::asOption)
                .orElseGet(None::None);
    }
}
