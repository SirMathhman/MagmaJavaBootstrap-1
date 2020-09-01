package com.meti.compile.node;

import com.meti.compile.type.Field;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class InlineDependents implements Dependents {
    private final List<Node> children;
    private final List<Field> fields;

    private InlineDependents(List<Field> fields, List<Node> children) {
        this.fields = Collections.unmodifiableList(fields);
        this.children = Collections.unmodifiableList(children);
    }

    public static InlineDependents toFields(Field pair) {
        return toFields(List.of(pair));
    }

    public static InlineDependents toFields(List<Field> fields) {
        return of(fields, Collections.emptyList());
    }

    public static InlineDependents ofChild(Node value) {
        return ofChildren(Collections.singletonList(value));
    }

    public static InlineDependents ofChildren(List<Node> children) {
        return of(Collections.emptyList(), children);
    }

    public static InlineDependents of(List<Field> fields, List<Node> children) {
        return new InlineDependents(fields, children);
    }

    public static InlineDependents ofSingleton(Field pair, Node node) {
        return of(Collections.singletonList(pair), Collections.singletonList(node));
    }

    public static Dependents ofChildren(Node... children) {
        return ofChildren(List.of(children));
    }

    @Override
    public <T> T apply(BiFunction<List<Field>, List<Node>, T> function) {
        return function.apply(fields, children);
    }

    @Override
    public Dependents copyChildren(List<Node> children) {
        return of(fields, children);
    }

    @Override
    public Dependents copyFields(List<Field> fields) {
        return new InlineDependents(fields, children);
    }

    @Override
    public Stream<Node> streamChildren() {
        return children.stream();
    }

    @Override
    public Stream<Field> streamFields() {
        return applyToFields(Collection::stream);
    }

    private <T> T applyToFields(Function<List<Field>, T> mapper) {
        return mapper.apply(fields);
    }
}
