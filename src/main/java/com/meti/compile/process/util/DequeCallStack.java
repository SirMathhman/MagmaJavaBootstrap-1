package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.Field;
import com.meti.util.TriFunction;

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
                .map(this::define)
                .collect(Collectors.toList());
    }

    @Override
    public Field define(Field field) {
        if(field.applyToName(this::isDefined)) {
            List<Type> options = field.applyToName(this::lookup);
            if(field.applyToType(options::contains)){
                throw field.applyDestruction(this::createAlreadyDefined);
            }
        }
        return field.apply(this::defineDeconstructed)
                .transform(field::withName);
    }

    private IllegalArgumentException createAlreadyDefined(String s, Type type, List<CallFlag> flags) {
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
