package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public interface DuadOption<A, B> {
    <R> Option<R> map(BiFunction<A, B, R> function);

    <R> TriadOption<A, B, R> extractStart(Function<A, R> function);

    <R> TriadOption<A, B, R> with(R value);

    DuadOption<A, B> filterEnd(Predicate<B> predicate);

    <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E;

    <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function) throws E;

    DuadOption<A, B> filterBoth(BiPredicate<A, B> predicate);

    Option<A> ignoreLast();

    <R> DuadOption<R, B> mapStart(Function<A, R> function);

    DuadOption<B, A> reverse();
}
