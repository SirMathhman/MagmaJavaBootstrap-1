package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ReturnToken implements Token {
	private final Token value;

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		consumer.accept(InlineDependents.ofChild(value));
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		return mapper.apply(InlineDependents.ofChild(value));
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Return);
	}

	@Override
	public Token copy(Dependents dependents) {
		return dependents.streamChildrenNatively()
				.findFirst().map(ReturnToken::new)
				.orElseThrow();
	}

	public ReturnToken(Token value) {
		this.value = value;
	}

	@Override
	public String render() {
		String renderedValue = value.render();
		return "return %s;".formatted(renderedValue);
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
