package com.meti.compile.process.util;

import com.meti.compile.type.Type;

import java.util.*;
import java.util.function.Function;

public class StackFrame {
    private final Map<String, Declaration> definitions = new HashMap<>();
    private final List<String> generics = new ArrayList<>();

    public String define(String name, Type type) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("A variable can't be blank.");
        }
        if (!definitions.containsKey(name)) {
            char first = name.charAt(0);
            boolean isValid = Character.isUpperCase(first)
                    || Character.isLowerCase(first)
                    || first == '_';
            String after = name.substring(1);
            for (char c : after.toCharArray()) {
                boolean isValidCharacter = (Character.isUpperCase(c)
                        || Character.isLowerCase(c)
                        || Character.isDigit(c)
                        || c == '_') && (c != ' ' && c != ',');
                if (!isValidCharacter) {
                    isValid = false;
                }
            }
            //TODO: Check for C keywords
            String nameToUse;
            if (isValid) {
                nameToUse = name;
            } else {
                nameToUse = "__%d__".formatted(name.hashCode());
            }
            definitions.put(name, new Declaration(nameToUse));
        }
        return definitions.get(name).define(type);
    }

    public boolean isDefined(String name) {
        return definitions.containsKey(name);
    }

    public List<Type> lookup(String name) {
        return definitions.get(name).listTypes();
    }

    @Override
    public String toString() {
        return definitions.keySet().toString();
    }

    public <T> Optional<T> lookup(String name, Function<Type, T> function) {
        return Optional.of(name)
                .filter(definitions::containsKey)
                .map(definitions::get)
                .map(definition -> definition.applyFirst(function));
    }

    public Optional<String> lookup(String name, Type type) {
        return Optional.of(name)
                .filter(definitions::containsKey)
                .map(definitions::get)
                .map(definition -> definition.lookup(type));
    }
}
