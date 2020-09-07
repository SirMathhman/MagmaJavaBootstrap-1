package com.meti.compile.node.primitive;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.EmptyDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class IntToken implements Token {
    private final int value;

    @Override
    public String toString() {
        return "'%d'".formatted(value);
    }

    public IntToken(int value) {
        this.value = value;
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(EmptyDependents.EmptyDependents());
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(EmptyDependents.EmptyDependents());
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Int);
    }

    @Override
    public Token copy(Dependents dependents) {
        return new IntToken(value);
    }

    @Override
    public String render() {
        return String.valueOf(value);
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
