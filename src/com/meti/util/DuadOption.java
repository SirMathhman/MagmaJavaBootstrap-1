package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface DuadOption<A, B> {
    <R> Option<R> map(BiFunction<A, B, R> function);

    <R> TriadOption<A, B, R> extractStart(Function<A, R> function);

    <R> TriadOption<A, B, R> with(R value);

    DuadOption<A, B> filterEnd(Predicate<B> predicate);

    <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E;
}
