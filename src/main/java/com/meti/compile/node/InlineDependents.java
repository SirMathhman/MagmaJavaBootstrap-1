package com.meti.compile.node;

import com.meti.compile.instance.Field;
import com.meti.util.CollectiveUtilities;
import com.meti.util.MonadStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.meti.util.Monad.Monad;
import static com.meti.util.MonadStream.Stream;

public final class InlineDependents implements Dependents {
    private final List<Token> children;
    private final List<Field> fields;
    private final Field properties;

    private InlineDependents(List<Token> children, List<Field> fields, Field properties) {
        this.fields = Collections.unmodifiableList(fields);
        this.children = Collections.unmodifiableList(children);
        this.properties = properties;
    }

    public static InlineDependents toFields(Field pair) {
        return toFields(List.of(pair));
    }

    public static InlineDependents toFields(List<Field> fields) {
        return of(fields, Collections.emptyList());
    }

    public static InlineDependents ofChild(Token value) {
        return ofChildren(Collections.singletonList(value));
    }

    public static InlineDependents ofChildren(List<Token> children) {
        return of(Collections.emptyList(), children);
    }

    public static InlineDependents of(List<Field> fields, List<Token> children) {
        return new InlineDependents(children, fields, null);
    }

    public static InlineDependents ofSingleton(Field pair, Token token) {
        return of(Collections.singletonList(pair), Collections.singletonList(token));
    }

    public static Dependents ofChildren(Token... children) {
        return ofChildren(List.of(children));
    }

    @Override
    public <T> T apply(BiFunction<List<Field>, List<Token>, T> function) {
        return function.apply(fields, children);
    }

    @Override
    public Dependents copyChildren(List<Token> children) {
        return of(fields, children);
    }

    @Override
    public Dependents copyFields(List<Field> fields) {
        return new InlineDependents(children, fields, null);
    }

    @Override
    public Stream<Token> streamChildrenNatively() {
        return children.stream();
    }

    @Override
    public Stream<Field> streamFieldsNatively() {
        return applyToFields(Collection::stream);
    }

    private <T> T applyToFields(Function<List<Field>, T> mapper) {
        return mapper.apply(fields);
    }

    @Override
    public MonadStream<Token> streamChildren() {
        return Stream(children);
    }

    @Override
    public Dependents identity() {
        return new InlineDependents(Collections.emptyList(), Collections.emptyList(), null);
    }

    @Override
    public Dependents append(Token child) {
        return Monad(children)
                .map(ArrayList::new)
                .append(child)
                .map(CollectiveUtilities::join)
                .append(fields)
                .apply((children1, fields1) -> new InlineDependents(children1, fields1, null));
    }

    @Override
    public MonadStream<Field> streamFields() {
        return Stream(fields);
    }

    @Override
    public <T> T applyToProperties(Function<Field, T> function) {
        if (properties == null) {
            throw new UnsupportedOperationException("Properties has not been provided.");
        } else {
            return function.apply(properties);
        }
    }

    @Override
    public Dependents copyProperties(Field properties) {
        return new InlineDependents(children, fields, properties);
    }

    @Override
    public DependentsBuilder withoutFields() {
        return new InlineBuilder(children, fields, properties);
    }

    private static class InlineBuilder implements DependentsBuilder {
        private final List<Field> fields;
        private final List<Token> children;
        private final Field properties;

        public InlineBuilder(List<Token> children, List<Field> fields, Field properties) {
            this.fields = fields;
            this.children = children;
            this.properties = properties;
        }

        @Override
        public Dependents build() {
            return new InlineDependents(children, fields, properties);
        }

        @Override
        public DependentsBuilder append(Field field) {
            return Monad(fields)
                    .append(field)
                    .map(CollectiveUtilities::join)
                    .append(children)
                    .reverse()
                    .with(properties)
                    .apply(InlineBuilder::new);
        }
    }
}
