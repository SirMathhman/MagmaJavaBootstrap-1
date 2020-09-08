package com.meti.util;

import java.util.List;

import static com.meti.util.Monad.Monad;

public class Triad<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public static <T> Triad<T, T, T> Triad(List<T> list) {
        if (list.size() != 3) {
            String message = "%d isn't equal to 3.".formatted(list.size());
            throw new IllegalArgumentException(message);
        } else {
            return new Triad<>(list.get(0), list.get(1), list.get(2));
        }
    }

    public Triad(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public <R> Monad<R> map(TriFunction<A, B, C, R> function) {
        return Monad(apply(function));
    }

    public Duad<A, B> withoutEnd() {
        return new Duad<>(a, b);
    }

    public <R> R apply(TriFunction<A, B, C, R> function) {
        return function.apply(a, b, c);
    }
}
