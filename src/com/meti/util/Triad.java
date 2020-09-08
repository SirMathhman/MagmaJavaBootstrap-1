package com.meti.util;

public class Triad<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public Triad(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public <R> Monad<R> implode(TriFunction<A, B, C, R> function) {
        return new Monad<>(function.apply(a, b, c));
    }

    public Duad<A, B> withoutEnd() {
        return new Duad<>(a, b);
    }
}
