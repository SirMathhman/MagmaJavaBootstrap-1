package com.meti.util;

import com.meti.compile.node.Token;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface DuadOption<A, B> {
    <R> MonadOption<R> map(BiFunction<A, B, R> function);

    <R> TriadOption<A, B, R> extractStart(Function<A, R> function);
}
