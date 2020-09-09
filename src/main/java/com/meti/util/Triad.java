package com.meti.util;

import java.util.List;
import java.util.function.Predicate;

import static com.meti.util.Monad.Monad;
import static com.meti.util.Some.Some;

public class Triad<A, B, C> {
    private final A start;
    private final B middle;
    private final C end;

    public static <T> Triad<T, T, T> Triad(List<T> list) {
        return Some(list)
                .extract(List::size)
                .filterEnd(size -> size == 3)
                .applyStartOrThrow(Triad::fromList, Triad::createInvalidLength);
    }

    private static <T> Triad<T, T, T> fromList(List<T> ts) {
        return Triad(ts.get(0), ts.get(1), ts.get(2));
    }

    private static IllegalArgumentException createInvalidLength(Option<Integer> option) {
        return option.map("%d isn't equal to 3."::formatted)
                .applyOrThrow(IllegalArgumentException::new);
    }

    private Triad(A start, B middle, C end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public static <A, B, C> Triad<A, B, C> Triad(A start, B middle, C end) {
        return new Triad<>(start, middle, end);
    }

    public <R> R applyByStart(Predicate<A> predicate, TriFunction<A, B, C, R> ifTrue, TriFunction<A, B, C, R> ifFalse) {
        return Some(start)
                .filter(predicate)
                .set(ifTrue, ifFalse)
                .apply(function -> function.apply(start, middle, end));
    }

    public <R> Monad<R> map(TriFunction<A, B, C, R> function) {
        return Monad(function).map(this::apply);
    }

    public Duad<A, B> ignoreEnd() {
        return new Duad<>(start, middle);
    }

    public <R> R apply(TriFunction<A, B, C, R> function) {
        return function.apply(start, middle, end);
    }
}
