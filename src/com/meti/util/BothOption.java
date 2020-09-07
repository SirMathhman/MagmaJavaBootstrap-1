package com.meti.util;

import java.util.function.BiFunction;

import static com.meti.util.Some.Some;

public class BothOption<A, B> implements DuadOption<A, B> {
    private final A value0;
    private final B value1;

    public BothOption(A value0, B value1) {
        this.value0 = value0;
        this.value1 = value1;
    }

    @Override
    public <R> MonadOption<R> map(BiFunction<A, B, R> function) {
        return Some(function.apply(value0, value1));
    }
}
