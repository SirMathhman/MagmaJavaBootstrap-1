package com.meti.compile.process.util;

import com.meti.compile.type.Type;
import com.meti.compile.type.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDeclaration implements Declaration {
    private final Map<Type, String> aliases = new HashMap<>();
    private final String value;
    private int counter = -2;
    private final List<CallFlag> flags;

    public MapDeclaration(String value, List<CallFlag> flags) {
        this.value = value;
        this.flags = flags;
    }

    @Override
    public String define(Field pair) {
        return pair.applyToType(this::define);
    }

    @Override
    public String define(Type type) {
        if (aliases.containsKey(type)) {
            throw new IllegalArgumentException("%s has already been defined with type %s".formatted(value, type));
        }
        String alias = next();
        aliases.put(type, alias);
        return alias;
    }

    @Override
    public List<Type> listTypes() {
        return new ArrayList<>(aliases.keySet());
    }

    private String next() {
        counter++;
        if (-1 == counter) return value;
        else return "%s%d_".formatted(value, counter);
    }

    @Override
    public String lookup(Type type) {
        if (aliases.containsKey(type)) {
            return aliases.get(type);
        } else {
            String message = ("An alias of type \"%s\" " +
                    "doesn't exist in the registered aliases of %s").formatted(type, aliases);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public List<CallFlag> flags() {
        return flags;
    }
}
