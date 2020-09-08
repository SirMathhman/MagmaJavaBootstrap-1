package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface DuadOption<A, B> {
    <R> Option<R> map(BiFunction<A, B, R> function);

    <R> TriadOption<A, B, R> extractStart(Function<A, R> function);

    <R> TriadOption<A, B, R> with(R value);
}
