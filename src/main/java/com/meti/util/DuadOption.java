package com.meti.util;

import java.util.function.*;

public interface DuadOption<A, B> {
    <R> Option<R> map(BiFunction<A, B, R> function);

    <R> TriadOption<A, B, R> extractStart(Function<A, R> function);

    <R> TriadOption<A, B, R> with(R value);

    DuadOption<A, B> filterEnd(Predicate<B> predicate);

    <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E;

    default <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function) throws E {
        return applyAllOrThrow(function);
    }

    <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function, Supplier<E> supplier) throws E;

    default DuadOption<A, B> filterBoth(BiPredicate<A, B> predicate) {
        throw new UnsupportedOperationException();
    }

    Option<A> ignoreLast();

    default <R> DuadOption<R, B> mapStart(Function<A, R> function) {
        throw new UnsupportedOperationException();
    }

    default DuadOption<B, A> reverse() {
        throw new UnsupportedOperationException();
    }

    default <R> DuadOption<A, R> mapEnd(Function<B, R> function) {
        throw new UnsupportedOperationException();
    }
}
