package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.compile.process.util.CallFlag;
import com.meti.compile.instance.Field;
import com.meti.compile.instance.FieldBuilder;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.block.FunctionType;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionToken implements Token {
    private final String name;
    private final List<Field> parameters;
    private final Type returnType;
    private final Token value;
    private final List<CallFlag> flags;

    public FunctionToken(List<CallFlag> flags, String name, List<Field> parameters, Type returnType, Token value) {
        this.parameters = Collections.unmodifiableList(parameters);
        this.returnType = returnType;
        this.value = value;
        this.name = name;
        this.flags = flags;
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        List<Field> fields = buildFields();
        List<Token> children = Collections.singletonList(value);
        Dependents dependents = InlineDependents.of(fields, children);
        consumer.accept(dependents);
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        List<Field> fields = buildFields();
        List<Token> children;
        if (value == null) {
            children = Collections.emptyList();
        } else {
            children = Collections.singletonList(value);
        }
        Dependents dependents = InlineDependents.of(fields, children);
        return mapper.apply(dependents);
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Function);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.apply(FunctionToken::copyImpl);
    }

    public static Token copyImpl(List<Field> fields, List<Token> tokens) {
        Field field = fields.get(0);
        List<Field> newParameters = fields.subList(1, fields.size());
        FunctionNodeBuilder builder = field.applyDestruction(FunctionToken::createBuilder)
                .withParameters(newParameters);
        if (!tokens.isEmpty()) builder = builder.withValue(tokens.get(0));
        return builder.build();
    }

    public static FunctionNodeBuilder createBuilder(String name, Type type, List<CallFlag> flags) {
        return type.streamChildren()
                .findFirst()
                .with(name)
                .with(flags)
                .applyDestructionOrThrow(FunctionToken::createBuilder, createNoReturnType(type));
    }

    private static FunctionNodeBuilder createBuilder(Type type, String name, List<CallFlag> flags) {
        return new FunctionNodeBuilder()
                .withName(name)
                .withReturnType(type)
                .withFlags(flags);
    }

    private static IllegalArgumentException createNoReturnType(Type type) {
        return new IllegalArgumentException("No return type was found in: " + type);
    }

    public List<Field> buildFields() {
        List<Field> fields = new ArrayList<>(createNamePair());
        fields.addAll(parameters);
        return fields;
    }

    public List<Field> createNamePair() {
        Collection<Type> paramTypes = parameters.stream().reduce(new ArrayList<>(), FunctionToken::append,
                (oldList, newList) -> newList);
        Type type = new FunctionType(returnType, paramTypes);
        Field pair = FieldBuilder.create().withName(name).withType(type).withFlags(flags).build();
        return List.of(pair);
    }

    public static List<Type> append(List<Type> oldList, Field field) {
        List<Type> newList = new ArrayList<>(oldList);
        field.applyToType(newList::add);
        return newList;
    }

    @Override
    public String render() {
        if (value != null) {
            String paramString = parameters.stream()
                    .map(Field::render)
                    .collect(Collectors.joining(",", "(", ")"));
            String renderedValue = value.render();
            return returnType.render(name + paramString + renderedValue);
        } else {
            return "";
        }
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.empty();
    }
}
