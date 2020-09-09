package com.meti.compile.node.primitive;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.EmptyDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CharToken implements Token {
	private final char value;

	public CharToken(char value) {
		this.value = value;
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(EmptyDependents.Empty);
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(EmptyDependents.Empty);
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Char);
	}

	@Override
	public Token copy(Dependents dependents) {
		return new CharToken(value);
	}

	@Override
	public String render() {
		return "'%s'".formatted(value);
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
