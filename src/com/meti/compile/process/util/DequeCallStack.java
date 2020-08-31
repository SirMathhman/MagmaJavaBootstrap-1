package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.*;
import java.util.stream.Collectors;

public final class DequeCallStack implements CallStack {
    private final Deque<StackFrame> frames = new LinkedList<>();


    public DequeCallStack() {
        enter();
    }

    @Override
    public void enter() {
        this.frames.add(new StackFrame());
    }

    @Override
    public List<TypePair> enter(List<TypePair> scope) {
        enter();
        return scope.stream()
                .map(this::define)
                .collect(Collectors.toList());
    }

    @Override
    public TypePair define(TypePair pair) {
        return pair.apply(this::defineDeconstructed)
                .transform(pair::withName);
    }

    private String defineDeconstructed(String name, Type type) {
        StackFrame frame = frames.peek();
        if (frame == null) {
            frame = new StackFrame();
            frames.push(frame);
        }
        return frame.define(name, type);
    }

    @Override
    public boolean isDefined(String name) {
        return frames.stream().anyMatch(frame -> frame.isDefined(name));
    }

    @Override
    public List<Type> lookup(String name) {
        return frames.stream()
                .filter(frame -> frame.isDefined(name))
                .map(frame -> frame.lookup(name))
                .findFirst()
                .orElse(Collections.emptyList());
    }

    @Override
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
