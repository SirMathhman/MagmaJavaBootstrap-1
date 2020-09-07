package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Duad<A, B> {
    private final A value0;
    private final B value1;

    public Duad(A value0, B value1) {
        this.value0 = value0;
        this.value1 = value1;
    }

    public Duad<B, A> swap() {
        return new Duad<>(value1, value0);
    }

    public <T> Monad<T> implode(BiFunction<A, B, T> function) {
        return new Monad<>(function.apply(value0, value1));
    }

    public <R> Triad<A, B, R> explodeFirst(Function<A, R> function) {
        return new Triad<>(value0, value1, function.apply(value0));
    }

    public <C> Triad<A, B, C> supply(C value) {
        return new Triad<>(value0, value1, value);
    }

    public <T> Duad<A, T> zipSecond(BiFunction<A, B, T> function) {
        return new Duad<>(value0, function.apply(value0, value1));
    }

    public <T> Duad<T, B> mapFirst(Function<A, T> function) {
        return new Duad<>(function.apply(value0), value1);
    }

    public <T> T apply(BiFunction<A, B, T> function) {
        return function.apply(value0, value1);
    }
}
