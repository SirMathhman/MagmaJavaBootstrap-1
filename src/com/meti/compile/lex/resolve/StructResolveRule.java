package com.meti.compile.lex.resolve;

import com.meti.compile.type.Type;
import com.meti.compile.type.TypeGroup;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class StructResolveRule implements ResolveRule {
    @Override
    public Optional<Type> resolve(String name) {
        return Optional.of(new StructType(name));
    }

    private static class StructType implements Type {
        private final String value;

        public StructType(String value) {
            this.value = value;
        }

        @Override
        public <T> T applyToGroup(Function<TypeGroup, T> function) {
            return function.apply(TypeGroup.Structure);
        }

        @Override
        public boolean matches(Type other) {
            return other.applyToGroup(TypeGroup.Structure::matches) && render().equals(other.render());
        }

        @Override
        public String render() {
            return render("");
        }

        @Override
        public String render(String name) {
            return "struct %s %s".formatted(value, name);
        }

        @Override
        public Stream<Type> streamChildren() {
            return Stream.empty();
        }
    }
}
