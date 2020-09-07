package com.meti.util;

import java.util.function.BiFunction;

public interface DuadOption<A, B> {
    <R> MonadOption<R> map(BiFunction<A, B, R> function);
}
