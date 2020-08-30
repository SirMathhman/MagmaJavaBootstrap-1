package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CallStack {
    private final Deque<StackFrame> frames = new LinkedList<>();


    public CallStack() {
        enter();
    }

    public void enter() {
        this.frames.add(new StackFrame());
    }

    public List<TypePair> enter(List<TypePair> scope) {
        enter();
        return scope.stream()
                .map(this::defineAsPair)
                .collect(Collectors.toList());
    }

    public TypePair defineAsPair(TypePair pair) {
        String newName = pair.apply(this::define);
        return pair.copy(newName);
    }

    @Deprecated
    public String define(String name, Type type) {
        assert frames.peek() != null;
        return frames.peek().define(name, type);
    }

    public void exit() {
        this.frames.pop();
    }

    public boolean isDefined(String name) {
        return frames.stream().anyMatch(frame -> frame.isDefined(name));
    }

    public List<Type> lookup(String name) {
        return frames.stream()
                .filter(frame -> frame.isDefined(name))
                .map(frame -> frame.lookup(name))
                .findFirst()
                .orElse(Collections.emptyList());
    }

    public <T> Optional<T> lookup(String name, Function<Type, T> function) {
        return frames.stream()
                .filter(frame -> frame.isDefined(name))
                .map(frame -> frame.lookup(name, function))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public Optional<String> lookup(String name, Type type) {
        return frames.stream()
                .filter(frame -> frame.isDefined(name))
                .map(frame -> frame.lookup(name, type))
                .flatMap(Optional::stream)
                .findFirst();
    }

    @Override
    public String toString() {
        return frames.toString();
    }
}
