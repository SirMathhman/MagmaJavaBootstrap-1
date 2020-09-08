package com.meti.util;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.meti.util.Monad.Monad;

public class MonadStream<T> {
    private final Stream<Monad<T>> stream;

    public MonadStream<T> filter(Predicate<T> predicate) {
        return Monad(predicate)
                .map(FunctionalUtilities::toFunction)
                .map(this::createFilter)
                .map(stream::filter)
                .apply(MonadStream::new);
    }

    private Predicate<Monad<T>> createFilter(Function<T, Boolean> tBooleanFunction) {
        return monad -> monad.apply(tBooleanFunction);
    }

    @SafeVarargs
    public static <R> MonadStream<R> Stream(R... values) {
        return Stream(List.of(values));
    }

    public static <R> MonadStream<R> Stream(Collection<R> collection) {
        return Stream(collection.stream());
    }

    private static <R> MonadStream<R> Stream(Stream<R> stream) {
        return new MonadStream<>(stream.map(Monad::Monad));
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
                .map(Monad::toOption)
                .orElseGet(None::None);
    }

    public <R> R reduce(R identity, BiFunction<R, T, R> function) {
        return stream.reduce(identity,
                (previous, monad) -> wrap(previous, monad, function),
                FunctionalUtilities.SelectLast());
    }

    private <R> R wrap(R previous, Monad<T> monad, BiFunction<R, T, R> function) {
        return monad.with(previous)
                .reverse()
                .apply(function);
    }

}
