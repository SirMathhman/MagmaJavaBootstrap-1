package com.meti.compile.lex.resolve.primitive;

import com.meti.compile.lex.resolve.FilteredResolveRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class VoidResolveRule extends FilteredResolveRule {
    @Override
    public boolean canResolve(String name) {
        return "Void".equals(name);
    }

    @Override
    public Type resolveImpl(String name) {
        return PrimitiveType.Void;
    }
}
