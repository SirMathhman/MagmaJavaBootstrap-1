package com.meti.compile.type.block;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypeGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionType implements Type {
    private final Collection<Type> parameterTypes;
    private final Type returnType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionType that = (FunctionType) o;
        return parameterTypes.containsAll(that.parameterTypes) &&
                that.parameterTypes.containsAll(parameterTypes) &&
                Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterTypes, returnType);
    }

    public FunctionType(Type returnType, Collection<Type> parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = Collections.unmodifiableCollection(parameterTypes);
    }

    @Override
    public <T> T applyToGroup(Function<TypeGroup, T> function) {
        return function.apply(TypeGroup.Function);
    }

    @Override
    public boolean matches(Type other) {
        return this == other;
    }

    @Override
    public String render(String name) {
        String parameterString = parameterTypes.stream()
                .map(Type::render)
                .collect(Collectors.joining(",", "(", ")"));
        String value = "(*%s)%s".formatted(name, parameterString);
        return returnType.render(value);
    }

    @Override
    public Stream<Type> streamChildren() {
        return Stream.concat(
                Stream.of(returnType),
                parameterTypes.stream()
        );
    }

    @Override
    public String toString() {
        return "FunctionType{" +
                "parameterTypes=" + parameterTypes +
                ", returnType=" + returnType +
                '}';
    }
}
