package com.meti.compile.lex.resolve.primitive;

import com.meti.compile.lex.resolve.FilteredResolveRule;
import com.meti.compile.instance.Type;
import com.meti.compile.instance.primitive.PrimitiveType;
import com.meti.compile.instance.reference.PointerType;

public class StringResolveRule extends FilteredResolveRule {
    @Override
    public boolean canResolve(String name) {
        return name.equals("String");
    }

    @Override
    public Type resolveImpl(String name) {
        return new PointerType(PrimitiveType.Char);
    }
}
