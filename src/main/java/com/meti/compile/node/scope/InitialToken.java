package com.meti.compile.node.scope;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.instance.FieldBuilder;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.Field;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class InitialToken implements Token {
	private final String name;
	private final Type type;
	private final Token value;

	public InitialToken(String name, Type type, Token value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	@Override
	public void acceptDependents(Consumer<Dependents> consumer) {
		//TODO: initial flags
		Field field = FieldBuilder.create().withName(name).withType(type).withFlags(Collections.emptyList()).build();
		Dependents dependents = InlineDependents.ofSingleton(field, value);
		consumer.accept(dependents);
	}

	@Override
	public <T> T applyToDependents(Function<Dependents, T> mapper) {
		Field field = FieldBuilder.create().withName(name).withType(type).withFlags(Collections.emptyList()).build();
		Dependents dependents = InlineDependents.ofSingleton(field, value);
		return mapper.apply(dependents);
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Initial);
	}

	@Override
	public Token copy(Dependents dependents) {
		return dependents.apply(InitialToken::copyImpl);
	}

	public static Token copyImpl(List<Field> fields, List<Token> tokens) {
		Field field = fields.get(0);
		Token value = tokens.get(0);
		return field.apply((name, type) -> new InitialNodeBuilder().withName(name).withType(type).withValue(value).build());
	}

	@Override
	public String render() {
		String renderedType = type.render(name);
		String renderedValue = value.render();
		return "%s=%s;".formatted(renderedType, renderedValue);
	}

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
