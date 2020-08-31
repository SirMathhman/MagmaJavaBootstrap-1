package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapStackFrame implements StackFrame {
    private final Map<String, Declaration> definitions = new HashMap<>();

    @Override
    public String define(TypePair pair) {
        return pair.applyToName(this::allocate).defineFrom(pair);
    }

    private Declaration allocate(String name) {
        if (!definitions.containsKey(name)) {
            String nameToUse = validateFirst(name) && validateContent(name) ? name : createInvalidName(name);
            definitions.put(name, new MapDeclaration(nameToUse));
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
}
