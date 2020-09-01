package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DequeCallStack implements CallStack {
    private final Deque<StackFrame> frames = new LinkedList<>();


    public DequeCallStack() {
        enter();
    }

    @Override
    public void enter() {
        this.frames.add(new MapStackFrame());
    }

    @Override
    public List<Field> enter(List<Field> scope) {
        enter();
        return scope.stream()
                .map(pair -> define(pair))
                .collect(Collectors.toList());
    }

    @Override
    public Field define(Field pair) {
        if(pair.applyToName(this::isDefined)) {
            List<Type> options = pair.applyToName(this::lookup);
            if(pair.applyToType(options::contains)){
                throw pair.apply(this::createAlreadyDefined);
            }
        }
        return pair.apply(this::defineDeconstructed)
                .transform(pair::withName);
    }

    private IllegalArgumentException createAlreadyDefined(String s, Type type) {
        String message = "%s has already been defined with type %s".formatted(s, type);
        return new IllegalArgumentException(message);
    }

    private String defineDeconstructed(Field pair) {
        StackFrame frame = frames.peek();
        if (frame == null) {
            frame = new MapStackFrame();
            frames.push(frame);
        }
        return frame.define(pair);
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

    @Override
    public Optional<List<CallFlag>> flags(String name) {
        return frames.stream()
                .filter(frame -> frame.isDefined(name))
                .map(frame -> frame.flags(name))
                .findFirst();
    }
}
