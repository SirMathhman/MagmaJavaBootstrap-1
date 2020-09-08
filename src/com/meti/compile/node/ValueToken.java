package com.meti.compile.node;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.meti.compile.node.EmptyDependents.Empty;

public class ValueToken implements Token {
    private final String value;

    public ValueToken(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueToken that = (ValueToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.of(value)
                .map(clazz::cast)
                .map(function);
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(Empty);
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(Empty);
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Value);
    }

    @Override
    public Token copy(Dependents dependents) {
        return new ValueToken(value);
    }

    @Override
    public String render() {
        return value;
    }
}
