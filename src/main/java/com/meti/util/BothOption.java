package com.meti.util;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.meti.util.Some.Some;

public class BothOption<A, B> implements DuadOption<A, B> {
    private final A start;
    private final B end;

    public BothOption(A start, B end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public <R> Option<R> map(BiFunction<A, B, R> function) {
        return Some(function.apply(start, end));
    }

    @Override
    public <R> TriadOption<A, B, R> extractStart(Function<A, R> function) {
        return new FullOption<>(start, end, function.apply(start));
    }

    @Override
    public <R> TriadOption<A, B, R> with(R value) {
        return new FullOption<>(start, end, value);
    }

    @Override
    public DuadOption<A, B> filterEnd(Predicate<B> predicate) {
        if(predicate.test(end)) {
            return new BothOption<>(start, end);
        } else {
            return new NeitherOption<>();
        }
    }

    @Override
    public <R, E extends Throwable> R applyStartOrThrow(Function<A, R> function, Function<Option<B>, E> supplier) throws E {
        return function.apply(start);
    }

    @Override
    public Option<A> ignoreLast() {
        return Some(start);
    }

    @Override
    public <R, E extends Throwable> R applyAllOrThrow(BiFunction<A, B, R> function) {
        return function.apply(start, end);
    }

    @Override
    public DuadOption<A, B> filterBoth(BiPredicate<A, B> predicate) {
        return new Duad<>(start, end).<DuadOption<A, B>>split(predicate, BothOption::new, (a, b) -> new NeitherOption<A, B>());
    }

    @Override
    public <R> DuadOption<R, B> mapStart(Function<A, R> function) {
        return new Monad<>(start)
                .map(function)
                .append(end)
                .apply(BothOption::new);
    }

    @Override
    public DuadOption<B, A> reverse() {
        return new BothOption<>(end, start);
    }
}
