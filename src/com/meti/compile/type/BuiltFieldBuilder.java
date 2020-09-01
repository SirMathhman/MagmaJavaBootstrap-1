package com.meti.compile.type;

import com.meti.compile.process.util.CallFlag;
import com.meti.util.TriFunction;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BuiltFieldBuilder {
    private final String name;
    private final Type type;
    private final List<CallFlag> flags;

    public BuiltFieldBuilder() {
        this(null, null, Collections.emptyList());
    }

    public BuiltFieldBuilder(String name, Type type, List<CallFlag> flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }


    public BuiltFieldBuilder withName(Supplier<String> name) {
        return new BuiltFieldBuilder(name.get(), type, flags);
    }

    public BuiltFieldBuilder withType(Supplier<Type> type) {
        return new BuiltFieldBuilder(name, type.get(), flags);
    }

    public BuiltFieldBuilder withName(String name) {
        return new BuiltFieldBuilder(name, type, flags);
    }

    public BuiltFieldBuilder withType(Type type) {
        return new BuiltFieldBuilder(name, type, flags);
    }

    public BuiltFieldBuilder withFlags(List<CallFlag> flags) {
        return new BuiltFieldBuilder(name, type, flags);
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
            return new BuiltFieldBuilder().withName(name).withType(type).withFlags(flags).build();
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