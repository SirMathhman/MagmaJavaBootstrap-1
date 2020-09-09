package com.meti.compile.node.structure;

import com.meti.compile.instance.Field;
import com.meti.compile.node.Dependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;
import com.meti.util.CollectiveUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.meti.compile.node.InlineDependents.toFields;

public class StructureToken implements Token {
    private final String name;
    private final List<Field> fields;

    public StructureToken(String name, List<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public <T, R> Optional<R> applyToContent(Class<? extends T> clazz, Function<T, R> function) {
        return Optional.of(name)
                .map(clazz::cast)
                .map(function);
    }

    @Override
    public void acceptDependents(Consumer<Dependents> consumer) {
        consumer.accept(toFields(fields));
    }

    @Override
    public <T> T applyToDependents(Function<Dependents, T> mapper) {
        return mapper.apply(toFields(fields));
    }

    @Override
    public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
        return mapper.apply(TokenGroup.Structure);
    }

    @Override
    public Token copy(Dependents dependents) {
        return dependents.streamFields()
                .reduceToMonad(new ArrayList<Field>(), CollectiveUtilities::join)
                .append(name)
                .reverse()
                .apply(StructureToken::new);
    }

    @Override
    public String render() {
        return "struct %s%s;".formatted(name, renderFields());
    }

    private String renderFields() {
        return fields.stream()
                .map(Field::render)
                .map("%s;"::formatted)
                .collect(Collectors.joining("", "{", "}"));
    }
}
