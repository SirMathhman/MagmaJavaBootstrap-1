package com.meti.compile.lex.resolve.primitive;

import com.meti.compile.lex.resolve.FilteredResolveRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

public class AnyResolveRule extends FilteredResolveRule {
    @Override
    public boolean canResolve(String name) {
        return name.equals("Any");
    }

    @Override
    public Type resolveImpl(String name) {
        return PrimitiveType.Any;
    }
}
