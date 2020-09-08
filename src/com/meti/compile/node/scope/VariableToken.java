package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.type.FieldBuilder;
import com.meti.compile.type.Field;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class VariableToken implements Token {
	private final String content;

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		//TODO: deprecate VariableNode.acceptDependents() in exchange of applyToContent
		Field pair = FieldBuilder.create().withName(content).withType(PrimitiveType.Unknown).withFlags(Collections.emptyList()).build();
		InlineDependents dependents = InlineDependents.toFields(pair);
		consumer.accept(dependents);
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		Field pair = FieldBuilder.create().withName(content).withType(PrimitiveType.Unknown).withFlags(Collections.emptyList()).build();
		InlineDependents dependents = InlineDependents.toFields(pair);
		return mapper.apply(dependents);
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Variable);
	}

	@Override
	public Token copy(Dependents dependents) {
		return dependents.streamFieldsNatively()
				.findFirst()
				.map(field -> field.applyToName(VariableToken::new))
				.orElseThrow();
	}

	public VariableToken(String content) {
		this.content = content;
	}

	@Override
	public String render() {
		return content;
	}

	@Override
	public String toString() {
		return "VariableNode{" +
		       "content='" + content + '\'' +
		       '}';
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
		T value = clazz.cast(content);
		R result = function.apply(value);
		return Optional.of(result);
	}
}
