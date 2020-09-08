package com.meti.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static com.meti.util.Monad.Monad;
import static com.meti.util.Some.Some;

public class Triad<A, B, C> {
    private final A start;
    private final B middle;
    private final C end;

    public static <T> Triad<T, T, T> Triad(List<T> list) {
        if (list.size() != 3) {
            String message = "%d isn't equal to 3.".formatted(list.size());
            throw new IllegalArgumentException(message);
        } else {
            return Triad(list.get(0), list.get(1), list.get(2));
        }
    }

    private Triad(A start, B middle, C end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public static <A, B, C> Triad<A, B, C> Triad(A start, B middle, C end) {
        return new Triad<A, B, C>(start, middle, end);
    }

    public <R> R mapByStart(Predicate<A> predicate, TriFunction<A, B, C, R> ifTrue, TriFunction<A, B, C, R> ifFalse) {
        return Some(start)
                .filter(predicate)
                .set(ifTrue, ifFalse)
                .apply(function -> function.apply(start, middle, end));
    }

    public <R> Monad<R> map(TriFunction<A, B, C, R> function) {
        return Monad(apply(function));
    }

    public Duad<A, B> withoutEnd() {
        return new Duad<>(start, middle);
    }

    public <R> R apply(TriFunction<A, B, C, R> function) {
        return function.apply(start, middle, end);
    }

    public <R> Duad<A, R> mergeTrailing(BiFunction<B, C, R> function) {
        return new Duad<>(start, function.apply(middle, end));
    }
}
