package com.meti.compile.process.util;

import com.meti.compile.type.Field;
import com.meti.compile.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapStackFrame implements StackFrame {
    private final Map<String, Declaration> definitions = new HashMap<>();

    @Override
    public String define(Field pair) {
        Declaration declaration = pair.applyToName(definitions::containsKey) ?
                pair.applyToName(definitions::get) :
                pair.applyDestruction((s, type, callFlags) -> initialize(s, callFlags));
        return declaration.define(pair);
    }

    private MapDeclaration initialize(String name, List<CallFlag> callFlags) {
        String nameToUse = createName(name, callFlags);
        MapDeclaration mapDeclaration = new MapDeclaration(nameToUse, callFlags);
        definitions.put(name, mapDeclaration);
        return mapDeclaration;
    }

    private String createName(String name, List<CallFlag> callFlags) {
        return callFlags.contains(CallFlag.NATIVE) || hasValidName(name) ? name : createInvalidName(name);
    }

    private boolean hasValidName(String name) {
        return validateFirst(name) && validateContent(name);
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
