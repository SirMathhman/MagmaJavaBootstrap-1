package com.meti.compile.process.util;

import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class MapStackFrame implements StackFrame {
    private final Map<String, Declaration> definitions = new HashMap<>();

    @Override
    public String define(Field pair) {
        return pair.apply(field -> field.applyToName(s -> allocate(s, field::createDeclaration))).defineFrom(pair);
    }

    private Declaration allocate(String name, Function<String, Declaration> function) {
        if (!definitions.containsKey(name)) {
            String nameToUse = validateFirst(name) && validateContent(name) ? name : createInvalidName(name);
            Declaration declaration = function.apply(nameToUse);
            definitions.put(name, declaration);
        }
        return definitions.get(name);
    }

    private String createInvalidName(String name) {
        return "__%d__".formatted(name.hashCode());
    }

    private boolean validateFirst(String name) {
        char first = name.charAt(0);
        return Character.isUpperCase(first)
                || Character.isLowerCase(first)
                || first == '_';
    }

    private boolean validateContent(String name) {
        return name.substring(1).chars()
                .mapToObj(c -> (char) c)
                .allMatch(this::isValidContent);
    }

    private boolean isValidContent(char c) {
        return (Character.isUpperCase(c)
                || Character.isLowerCase(c)
                || Character.isDigit(c)
                || c == '_') && (c != ' ' && c != ',');
    }

    @Override
    public boolean isDefined(String name) {
        return definitions.containsKey(name);
    }

    @Override
    public List<Type> lookup(String name) {
        return definitions.get(name).listTypes();
    }

    @Override
    public String toString() {
        return definitions.keySet().toString();
    }

    @Override
    public Optional<String> lookup(String name, Type type) {
        return Optional.of(name)
                .filter(definitions::containsKey)
                .map(definitions::get)
                .map(definition -> definition.lookup(type));
    }

    //TODO: replace flags with apply function
    @Override
    public List<CallFlag> flags(String name) {
        return definitions.get(name).flags();
    }
}
