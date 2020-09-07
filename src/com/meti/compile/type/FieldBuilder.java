package com.meti.compile.type;

import com.meti.compile.process.util.CallFlag;
import com.meti.util.TriFunction;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FieldBuilder {
    private final String name;
    private final Type type;
    private final List<CallFlag> flags;

    public FieldBuilder() {
        this(null, null, Collections.emptyList());
    }

    public FieldBuilder(String name, Type type, List<CallFlag> flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    public FieldBuilder withName(Supplier<String> name) {
        return new FieldBuilder(name.get(), type, flags);
    }

    public FieldBuilder withType(Supplier<Type> type) {
        return new FieldBuilder(name, type.get(), flags);
    }

    public FieldBuilder withName(String name) {
        return new FieldBuilder(name, type, flags);
    }

    public FieldBuilder withType(Type type) {
        return new FieldBuilder(name, type, flags);
    }

    public FieldBuilder withFlags(List<CallFlag> flags) {
        return new FieldBuilder(name, type, flags);
    }

    public BuiltField build() {
        return new BuiltField(name, type, flags);
    }

    public static class BuiltField implements Field {
        private final Type type;
        private final String name;
        private final List<CallFlag> flags;

        public BuiltField(String name, Type type, List<CallFlag> flags) {
            this.name = name;
            this.type = type;
            this.flags = flags;
        }

        @Override
        public <T> T apply(BiFunction<String, Type, T> function) {
            return function.apply(name, type);
        }

        @Override
        public <T> T applyToName(Function<String, T> function) {
            return function.apply(name);
        }

        @Override
        public <T> T applyToType(Function<Type, T> function) {
            return function.apply(type);
        }

        @Override
        public String render() {
            return type.render(name);
        }

        @Override
        public Field withName(String name) {
            return new FieldBuilder().withName(name).withType(type).withFlags(flags).build();
        }

        @Override
        public <T> T applyDestruction(TriFunction<String, Type, List<CallFlag>, T> function) {
            return function.apply(name, type, flags);
        }

        @Override
        public <T> T apply(Function<Field, T> function) {
            return function.apply(this);
        }

        @Override
        public Field acceptType(Consumer<Type> consumer) {
            consumer.accept(type);
            return this;
        }

    }
}